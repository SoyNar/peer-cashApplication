package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestmentResponseDto {

    private Long investorId;
    private Long loanId;
    private Long investmentId;
    private Long applicantId;
    private String nameApplicant;
    private String nameInvestor;
    private String statusInvestment;
    private BigDecimal expectGain;
    private BigDecimal amount;

}
