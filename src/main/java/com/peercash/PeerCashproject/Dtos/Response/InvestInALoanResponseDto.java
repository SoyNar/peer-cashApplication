package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestInALoanResponseDto {

    private LocalDate nextPaymentDate;
    private Long investmentId;
    private  String message;
    private Long investorId;
    private BigDecimal gain;
    private LocalDate firstInstallment;
}
