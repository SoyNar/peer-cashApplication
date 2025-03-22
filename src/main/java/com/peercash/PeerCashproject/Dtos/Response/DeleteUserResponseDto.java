package com.peercash.PeerCashproject.Dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteUserResponseDto {
    private Long id;
    private String email;
    private String name;
}
