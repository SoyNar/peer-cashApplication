package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Response.InvestmentResponseDto;
import com.peercash.PeerCashproject.Models.Investment;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Repository.InvestmentRepository;
import com.peercash.PeerCashproject.Service.IService.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/peer-cash/investment")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping("/investment")
    public ResponseEntity<List<InvestmentResponseDto>> getAllInvestmentByInvestor(Long investorId){
        List<InvestmentResponseDto> investment = this.investmentService.getInvestmentByInvestor(investorId);
       return ResponseEntity.status(HttpStatus.OK).body(investment);
    }
}
