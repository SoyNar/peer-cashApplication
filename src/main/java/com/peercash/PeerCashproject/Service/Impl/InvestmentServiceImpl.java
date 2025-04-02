package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Response.InvestmentResponseDto;
import com.peercash.PeerCashproject.Models.Investment;
import com.peercash.PeerCashproject.Repository.InvestmentRepository;
import com.peercash.PeerCashproject.Service.IService.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
 @RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

     private final InvestmentRepository investmentRepository;

     @Transactional
    @Override
    public List<InvestmentResponseDto> getInvestmentByInvestor(Long investorId) {
        List<Investment> investment = this.investmentRepository
                .findByInvestorId(investorId);
        return investment.stream()
                .map(this::mapToInvestmentResponseDto)
                .collect(Collectors.toList());
    }

    private InvestmentResponseDto mapToInvestmentResponseDto(Investment investment) {
       return    InvestmentResponseDto.builder()
                 .loanId(investment.getLoan().getId())
                 .investmentId(investment.getId())
                 .statusInvestment(investment.getStatusTransaction().toString())
                 .amount(investment.getAmount())
                 .nameInvestor(investment.getInvestor().getName())
                 .expectGain(investment.getExpectedGain())
                 .build();
    }
}
