package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Request.ApplyLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.AppyLoanResponseDto;

public interface ApplicantUserService {

    AppyLoanResponseDto applyForALoan(ApplyLoanRequestDto applyLoanRequestDto, Long userId);
}
