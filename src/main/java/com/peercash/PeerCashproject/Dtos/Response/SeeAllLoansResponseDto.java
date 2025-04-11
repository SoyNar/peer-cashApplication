package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeeAllLoansResponseDto {
    private Long applicantId;
    private String statusLoan;
    private String reason;
    private Long loanId;
    private String payDay;
    private int numberOfInstallments;



}
