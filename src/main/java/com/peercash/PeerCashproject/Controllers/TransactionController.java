package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.PaymentLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.PaymentLoanResponseDto;
import com.peercash.PeerCashproject.Service.IService.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/peer-cash/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @PostMapping("/pay-dues/{userId}/{loanId}")
    public ResponseEntity<PaymentLoanResponseDto> paymentLoan(
            @RequestBody PaymentLoanRequestDto requestDto,
            @PathVariable Long userId,
            @PathVariable Long loanId){
        PaymentLoanResponseDto responseDto = this.transactionService.paymentLoan(requestDto,userId,loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
