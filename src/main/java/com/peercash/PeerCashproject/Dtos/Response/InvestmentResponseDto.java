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
    private String nameInvestor;
    private String statusPayment;
    private BigDecimal expectGain;
    private BigDecimal amount;

}
