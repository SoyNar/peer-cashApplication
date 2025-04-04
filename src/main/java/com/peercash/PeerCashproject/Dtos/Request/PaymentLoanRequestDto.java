package com.peercash.PeerCashproject.Dtos.Request;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLoanRequestDto {
    private Long userId;
    private String description;
    private BigDecimal amount;
    private String reference;
    private String paymentMethod;
    private String dateTransaction;

}
