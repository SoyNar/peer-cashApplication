package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Response.GetAllLoanPendingDto;
import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Enums.StatusT;
import com.peercash.PeerCashproject.Exceptions.Custom.*;
import com.peercash.PeerCashproject.Models.Investment;
import com.peercash.PeerCashproject.Models.Investor;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Repository.InvestmentRepository;
import com.peercash.PeerCashproject.Repository.InvestorRepository;
import com.peercash.PeerCashproject.Repository.LoanRepository;
import com.peercash.PeerCashproject.Service.IService.IEmailService;
import com.peercash.PeerCashproject.Service.IService.InvestmentsUtilsService;
import com.peercash.PeerCashproject.Service.IService.InvestorUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestorUserServiceImpl implements InvestorUserService

{
    private final LoanRepository loanRepository;
    private final InvestorRepository investorRepository;
    private  final IEmailService emailService;
   private final InvestmentRepository investmentRepository;
   private final InvestmentsUtilsService utilsService;

    @Override
    @Transactional
    public List<GetAllLoanPendingDto> getAllLoanPending() {
        List<Loans> loans = this.loanRepository.findLoansByStatusLoan(StatusLoan.PENDING);
        return loans.stream().map(this::mapToGetLoanPending).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<LocalDate> investInALoan(Long loanId, Long investorId) {

        Loans loan = this.loanRepository.findById(loanId).orElseThrow(()
                -> new EntityNotFoundException("No encontrado"));

        if(!loan.getStatusLoan().equals(StatusLoan.PENDING)) {
            throw new IBadRequestExceptions("solo puede invetir en prestamos pendientes");
        }

        Investor  investor = this.investorRepository.findByRoleName("ROLE_INVESTOR",investorId).orElseThrow(()
                -> new UserNotFondException("Erro: no encontrado o no tiene el rol investor"));

        if(!investor.isActive()){
            throw new UnauthorizedActionException("El usuario no puede invetir, porque su cuenta no ha sido activada");

        }

        loan.setStatusLoan(StatusLoan.APPROVED);
        loan.setDateApproval(LocalDate.now());
        int weeks = loan.getNumberOfInstallment();
        List<LocalDate> getPayDates= calculatePaymentDates(LocalDate.now(),weeks);
        loan.setPayDay(LocalDate.now());
        this.loanRepository.save(loan);

        BigDecimal totalGainInvestor = calculateGainOfInvestor(loan);

        Investment createInvestment = Investment.builder()
                .date(LocalDate.now())
                .expectedGain(loan.getRequestAmount())
                .investor(investor)
                .loan(loan)
                .statusTransaction(StatusT.PENDING)
                .expectedGain(totalGainInvestor)
                .build();
        this.investmentRepository.save(createInvestment);

        Map<String, Object> emailBody = new HashMap<>();
        emailBody.put("Loan ID", loan.getId());
        emailBody.put("Loan Amount", loan.getRequestAmount().toString());
        emailBody.put("Investor Name", investor.getName());
        emailBody.put("Status", "Approved");

       emailService.sendEmail(investor.getEmail(),
               "Inversion en prestamo",emailBody);


        return getPayDates;
    }

    @Transactional
    @Override
    public String rejectLoan(Long loanId, Long investorId) {

        Loans loan = this.loanRepository.findById(loanId).orElseThrow(()
                -> new EntityNotFoundException("No encontrado"));

        if(!loan.getStatusLoan().equals(StatusLoan.PENDING)) {
            throw new IBadRequestExceptions("solo puede rechazar  prestamos pendientes");
        }

        Investor  investor = this.investorRepository.findByRoleName("ROLE_INVESTOR",investorId).orElseThrow(()
                -> new RoleNotFoundException("Error: no encontrado o no tiene el rol investor"));

        if(!investor.isActive()){
            throw new UnauthorizedActionException("Error: El usuario no puede realizar esta accion, porque su cuenta no ha sido activada");

        }

        loan.setStatusLoan(StatusLoan.REJECTED);
        this.loanRepository.save(loan);

        return "Accion realizada con exito";
    }

    private GetAllLoanPendingDto mapToGetLoanPending(Loans loans) {
        return GetAllLoanPendingDto.builder()
                .amount(loans.getRequestAmount().doubleValue())
                .reason(loans.getReason())
                .applicantId(loans.getApplicant().getId())
                .numberOfInstallments(loans.getNumberOfInstallment())
                .status(loans.getStatusLoan().toString())
                .build();
}

private List<LocalDate> calculatePaymentDates(LocalDate startDate, int weeks){
        List<LocalDate> paymentDates = new ArrayList<>();
        LocalDate paymentDate = startDate;

    for (int i = 0; i < weeks; i++) {
        paymentDate = paymentDate.plusWeeks(1);
        paymentDates.add(paymentDate);
    }
    return paymentDates;

}

/**
 * calcular la ganancia de un inversor
 * seria el 80% de  lo que deja el porcentaje de intereses
 * */
 private BigDecimal calculateGainOfInvestor( Loans loan){
     BigDecimal interestRate = new BigDecimal("0.04");
     BigDecimal amount = loan.getRequestAmount();
     int numberOfInstallments = loan.getNumberOfInstallment();

     BigDecimal totalWithInterest= this.utilsService.calculateTotalWithInterest(amount, interestRate, numberOfInstallments);
     BigDecimal totalInterest = totalWithInterest.subtract(amount);
     return totalInterest.multiply(new BigDecimal("0.80")).setScale(2, RoundingMode.HALF_UP);
 }


}
