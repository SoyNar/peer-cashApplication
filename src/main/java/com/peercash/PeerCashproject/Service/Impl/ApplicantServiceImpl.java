package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;
import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.Applicant;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Repository.ApplicantRepository;
import com.peercash.PeerCashproject.Service.IService.ApplicantUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl  implements ApplicantUserService {


   private final ApplicantRepository applicantRepository;

    @Override
    public AppyLoanResponseDto applyForALoan(ApplyLoanRequestDto applyLoanRequestDto, Long userId) {

        Applicant applicant = this.applicantRepository.findById(userId).orElseThrow(()
                -> new UserNotFondException("usuario no existe"));

        if (!applicant.isActive()){
            throw new IBadRequestExceptions("el usuario no puede hacer prestamos");
        }


        Loans loans = Loans.builder()
                .applicant(applicant)
                .details(applyLoanRequestDto.getDetails())
                .statusLoan(StatusLoan.PENDING)
                .numberOfInstallment(4)
                .reason(applyLoanRequestDto.getReason())
                .requestAmount(applyLoanRequestDto.getAmount())
                .payDay(LocalDate.now().plusDays(30))
                .build();
        return AppyLoanResponseDto.builder()
                .message("Prestamo solicitado con exito")
                .payday(loans.getPayDay().toString())
                .userId(loans.getApplicant().getId())
                .reason(loans.getReason())
                .build();
    }
}
