package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentLoanResponseDto {
    private Long id;
    private Long userId;
    private String reference;
    private String paymentMethod;
    private String dateTransaction;
    private String description;
    private BigDecimal amount;
    private String statusTransaction;
}
