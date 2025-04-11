package com.peercash.PeerCashproject.Service.IService;


import com.peercash.PeerCashproject.Dtos.Request.PaymentLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.PaymentLoanResponseDto;

public interface ITransactionService {
PaymentLoanResponseDto paymentLoan(PaymentLoanRequestDto requestDto,Long userId,Long loanId);
}
