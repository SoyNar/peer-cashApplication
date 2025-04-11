package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppyLoanResponseDto {
    private Long userId;
    private String reason;
    private  String payday;
    private String message;
}
