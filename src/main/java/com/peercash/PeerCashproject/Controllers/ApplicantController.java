package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;
import com.peercash.PeerCashproject.Service.IService.ApplicantUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/peer-cash/applicant")
@RestController
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantUserService applicantUserService;

    @PostMapping("/apply-loan/{userId}")
    public ResponseEntity<AppyLoanResponseDto> applyForALoan(ApplyLoanRequestDto loanRequestDto, Long userId){
        AppyLoanResponseDto appyLoanResponseDto = this.applicantUserService.applyForALoan(loanRequestDto, userId);

        return ResponseEntity.status(HttpStatus.OK).body(appyLoanResponseDto);
    }
}
