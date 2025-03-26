package com.peercash.PeerCashproject.Dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLoanRequestDto {


    private String reason;
    private String description;
    private String details;
    private BigDecimal amount;
    private LocalDate paymentDay;
    private int numberOfInstallment;
    private String statusLoan;
}
