package com.peercash.PeerCashproject.Dtos.Response;

import com.peercash.PeerCashproject.Models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegisterResponseDto {
    private Long id;
    private String document;
    private String email;
    private String name;
    private String lastname;
    private LocalDate birthday;

}
