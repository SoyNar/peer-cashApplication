package com.peercash.PeerCashproject.Dtos.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDto {
    private String email;
    private String password;
}
