package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestForApprovalResponseDto {
    private boolean active;
    private String urlDocument;
    private String urlBankAccount;
    private String document;
    private String birthday;
    private String name;
    private String email;
    private String bankAccount;
    private String lastname;
}
