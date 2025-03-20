package com.peercash.PeerCashproject.Dtos.Request;

import com.peercash.PeerCashproject.Models.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class RegisterRequestDto {
    private String username;
    private String document;
    private String email;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String bankAccount;
    private String userType;
    private List<String> roles;

}
