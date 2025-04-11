package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;
import com.peercash.PeerCashproject.Service.IService.ApplicantUserService;
import com.peercash.PeerCashproject.Service.IService.ILoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/peer-cash/loan")
@RequiredArgsConstructor
@RestController
public class LoanController {

    private final ILoanService loanService;

    @PostMapping("/apply-loan/{userId}")
    public ResponseEntity<AppyLoanResponseDto> applyForALoan(
            @Valid
            @RequestBody ApplyLoanRequestDto
                    loanRequestDto,
            @PathVariable Long userId){
        AppyLoanResponseDto appyLoanResponseDto = this.loanService.applyForALoan(loanRequestDto,
                userId);

        return ResponseEntity.status(HttpStatus.OK).body(appyLoanResponseDto);
    }

    @GetMapping("/loans")
    public ResponseEntity<List<SeeAllLoansResponseDto>> getAllLoans(){
        List<SeeAllLoansResponseDto> allLoans = this.loanService.seeAllLoans();
        return  ResponseEntity.status(HttpStatus.OK).body(allLoans);
    }
}
