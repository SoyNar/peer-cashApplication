package com.peercash.PeerCashproject.Dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetAllUsersDto {
    private Long id;
    private String name;
    private String lastname;
    private String document;
    private boolean active;
    private String backAccount;
    private String email;
    private  String role;

}
