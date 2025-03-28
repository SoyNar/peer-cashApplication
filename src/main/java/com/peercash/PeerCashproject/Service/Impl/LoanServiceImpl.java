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

import java.time.LocalDate;
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
    public AppyLoanResponseDto applyForALoan(ApplyLoanRequestDto
                                                         applyLoanRequestDto,
                                             Long userId) {

        Applicant applicant = this.applicantRepository.findByRoleName("ROLE_APPLICANT",userId).orElseThrow(()
                -> new UserNotFondException("usuario no existe  o no es un aplicante"));

        if (!applicant.isActive()){
            throw new IBadRequestExceptions("el usuario no puede hacer prestamos");
        }

        /**
         * Verificar
         * que el usuario no tenga prestamos pendietes por pagar
         * que el usario este activo
         * que el usuario tenga buena calificacion en la aplicacion
         * */


        Loans loans = Loans.builder()
                .applicant(applicant)
                .statusLoan(StatusLoan.PENDING)
                .numberOfInstallment(4)
                .reason(applyLoanRequestDto.getReason())
                .requestAmount(applyLoanRequestDto.getAmount())
                .payDay(LocalDate.now().plusDays(30))
                .build();
        this.loanRepository.save(loans);

        return AppyLoanResponseDto.builder()
                .message("Prestamo solicitado con exito")
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


}
