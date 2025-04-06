package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllLoanPendingDto {
    private int numberOfInstallments;
    private Long applicantId;
    private String reason;
    private String status;
    private double amount;
}
