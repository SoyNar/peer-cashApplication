package com.peercash.PeerCashproject.Dtos.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivateAccountUserDto {

    private String message;
    private Long userId;
    private boolean activeUser;
    private String email;
    private String name;
}
