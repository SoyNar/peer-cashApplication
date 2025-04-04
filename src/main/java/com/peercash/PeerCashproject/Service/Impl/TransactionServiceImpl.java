package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.PaymentLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.PaymentLoanResponseDto;
import com.peercash.PeerCashproject.Service.IService.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    @Override
    public PaymentLoanResponseDto paymentLoan(PaymentLoanRequestDto requestDto) {

        return null;
    }
}
