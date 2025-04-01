package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;
import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.Applicant;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Repository.ApplicantRepository;
import com.peercash.PeerCashproject.Repository.LoanRepository;
import com.peercash.PeerCashproject.Service.IService.ILoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl  implements ILoanService {

    private final ApplicantRepository applicantRepository;
    private final LoanRepository loanRepository;

    /**
     * metodo para crear un prestamo
     * un usuario de tipo applicant y con el role ROLE_APPLICANT puede crear un prestamo
     * si el usuario tiene role ROLE_INVESTOR  no puede crear un prestamo
     * */
    @Override
    public AppyLoanResponseDto applyForALoan(ApplyLoanRequestDto applyLoanRequestDto, Long userId) {


        BigDecimal minimumAmount = new BigDecimal("100000");
        BigDecimal maximumAmount = new BigDecimal("2000000");
        BigDecimal requestedAmount = applyLoanRequestDto.getAmount();

        List<Integer> availableMonths = getMonthOptions(requestedAmount);
        int selectedMonths = applyLoanRequestDto.getNumberOfInstallments();

        if (!availableMonths.contains(selectedMonths)) {
            throw new IBadRequestExceptions("Error: Los meses seleccionados no son válidos para el monto solicitado");
        }

        int weeks = selectedMonths * 4;

        BigDecimal weeklyPayment = calculateWeeklyPayment(requestedAmount, selectedMonths);

        Applicant applicant = this.applicantRepository.findByRoleName("ROLE_APPLICANT", userId)
                .orElseThrow(() -> new UserNotFondException("Usuario no existe o no es un aplicante"));

        if (!applicant.isActive()) {
            throw new IBadRequestExceptions("El usuario no puede hacer préstamos");
        }

        validationApplicantLoan(userId);

        if (requestedAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IBadRequestExceptions("Error: el monto no puede ser cero");
        }

        if (requestedAmount.compareTo(minimumAmount) < 0 || requestedAmount.compareTo(maximumAmount) > 0) {
            throw new IBadRequestExceptions("Error: el mínimo debe ser 100000 y el máximo 2000000");
        }

        validateDebtCapacity(applyLoanRequestDto);

        Loans loans = Loans.builder()
                .applicant(applicant)
                .statusLoan(StatusLoan.PENDING)
                .numberOfInstallment(weeks)
                .monthlyInstallment(weeklyPayment)
                .totalDue(weeklyPayment.multiply(new BigDecimal(weeks)))
                .reason(applyLoanRequestDto.getReason())
                .requestAmount(requestedAmount)
                .payDay(LocalDate.now().plusWeeks(1))
                .build();

        this.loanRepository.save(loans);

        return AppyLoanResponseDto.builder()
                .message("Préstamo solicitado con éxito")
                .payday(loans.getPayDay().toString())
                .userId(loans.getApplicant().getId())
                .reason(loans.getReason())
                .build();
    }


    @Override
    public List<SeeAllLoansResponseDto> seeAllLoans() {
        List<Loans> loansList = this.loanRepository.findAll();
        return loansList.stream().map(this::mapToGetLoans).collect(Collectors.toList());
    }

    private SeeAllLoansResponseDto mapToGetLoans(Loans loans){
        return SeeAllLoansResponseDto.builder()
                .loanId(loans.getId())
                .applicantId(loans.getApplicant().getId())
                .numberOfInstallments(loans.getNumberOfInstallment())
                .payDay(loans.getPayDay().toString())
                .reason(loans.getReason())
                .statusLoan(loans.getStatusLoan().toString())
                .build();

    }

    private void validationApplicantLoan(Long applicantId){
        if(this.loanRepository.existsByApplicantIdAndStatusLoan(applicantId,StatusLoan.PENDING)){
            throw new IBadRequestExceptions("Aun tiene prestamos pendientes");
        }
        if(this.loanRepository.existsByApplicantIdAndStatusLoan(applicantId,StatusLoan.DELINQUENT)){
            throw new IBadRequestExceptions("Debes un prestamo anteriror");
        }
    }

    /**
     * validar capacidad de endeudamiento
     * */
    private void validateDebtCapacity(ApplyLoanRequestDto requestDto){
        BigDecimal monthlyIncome = requestDto.getMonthlyIncome();
        BigDecimal monthlyExpenses = requestDto.getMonthlyExpenses();

        if (monthlyIncome == null || monthlyExpenses == null) {
            throw new IBadRequestExceptions("Los ingresos o los gastos no pueden ser nulos.");
        }

        if(monthlyExpenses.compareTo(monthlyIncome) > 0){
            throw new IBadRequestExceptions("Sus gastos son mayores a sus ingresos.");
        }

        BigDecimal maxDebtPayment = monthlyIncome.multiply(new BigDecimal("0.40"));

        BigDecimal interestRate = new BigDecimal("0.04");
        BigDecimal totalDue = requestDto.getAmount().multiply(BigDecimal.ONE.add(interestRate).pow(requestDto.getNumberOfInstallments()));
        BigDecimal monthlyLoanPayment = totalDue.divide(new BigDecimal(requestDto.getNumberOfInstallments()), 2, RoundingMode.HALF_UP);

        if (monthlyLoanPayment.compareTo(maxDebtPayment) > 0) {
            throw new IBadRequestExceptions("La cuota mensual del préstamo excede su capacidad de pago.");
        }

        BigDecimal debtToIncomeRatio = monthlyExpenses.add(monthlyLoanPayment)
                .divide(monthlyIncome, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

        BigDecimal maxDTIRatio = new BigDecimal("40");

        if (debtToIncomeRatio.compareTo(maxDTIRatio) > 0) {
            throw new IBadRequestExceptions("La relación deuda-ingreso es demasiado alta, no se puede solicitar el préstamo.");
        }
    }



    private List<Integer> getMonthOptions(BigDecimal amount) {
        List<Integer> months = new ArrayList<>();
        if (amount.compareTo(new BigDecimal("100000")) >= 0 && amount.compareTo(new BigDecimal("200000")) < 0) {
            months.add(2);
        } else if (amount.compareTo(new BigDecimal("200000")) >= 0 && amount.compareTo(new BigDecimal("500000")) < 0) {
            months.add(2);
            months.add(3);
            months.add(4);
        } else if (amount.compareTo(new BigDecimal("500000")) >= 0 && amount.compareTo(new BigDecimal("800000")) < 0) {
            months.add(4);
            months.add(5);
            months.add(6);
        } else if (amount.compareTo(new BigDecimal("800000")) >= 0 && amount.compareTo(new BigDecimal("1200000")) < 0) {
            months.add(4);
            months.add(5);
            months.add(6);
            months.add(7);
        } else if (amount.compareTo(new BigDecimal("1200000")) >= 0 && amount.compareTo(new BigDecimal("1600000")) < 0) {
            months.add(6);
            months.add(7);
            months.add(8);
        } else if (amount.compareTo(new BigDecimal("1600000")) >= 0 && amount.compareTo(new BigDecimal("2000000")) <= 0) {
            months.add(7);
            months.add(8);
            months.add(9);
            months.add(10);
        }
        return months;
    }
    private BigDecimal calculateWeeklyPayment(BigDecimal amount, int months) {
        int weeks = months * 4;
        BigDecimal interestRate = new BigDecimal("0.04");
        BigDecimal interestFactor = BigDecimal.ONE.add(interestRate).pow(months);
        BigDecimal totalDue = amount.multiply(interestFactor).setScale(2, RoundingMode.HALF_UP);
        return totalDue.divide(new BigDecimal(weeks), 2, RoundingMode.HALF_UP);
    }
}
