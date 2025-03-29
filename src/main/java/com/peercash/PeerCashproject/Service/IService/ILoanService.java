package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;

import java.util.List;

public interface ILoanService {
    AppyLoanResponseDto applyForALoan(ApplyLoanRequestDto applyLoanRequestDto, Long userId);
    List<SeeAllLoansResponseDto> seeAllLoans();

}
