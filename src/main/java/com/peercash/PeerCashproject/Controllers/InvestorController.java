package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Response.GetAllLoanPendingDto;
import com.peercash.PeerCashproject.Service.IService.InvestorUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/peer-cash/investos")
@RequiredArgsConstructor
public class InvestorController {

    private final InvestorUserService investorUserService;


    @GetMapping("/loans-pending")
    public ResponseEntity<List<GetAllLoanPendingDto>> getAllLoanPending() {
        List<GetAllLoanPendingDto> getAllLoanPendingDto = this.investorUserService.getAllLoanPending();
        return ResponseEntity.status(HttpStatus.OK).body(getAllLoanPendingDto);
    }

    @PutMapping("/approved-loan")
    public ResponseEntity<String> approvedLoan(Long loanId, Long investorId) {

        String status = this.investorUserService.investInALoan(loanId, investorId);
        return  ResponseEntity.status(HttpStatus.OK).body(status);
    }
}
