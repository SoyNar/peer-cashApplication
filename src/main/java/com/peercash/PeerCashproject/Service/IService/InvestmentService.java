package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Response.InvestmentResponseDto;
import java.util.List;

public interface InvestmentService {
    List<InvestmentResponseDto> getInvestmentByInvestor(Long investorId);
}
