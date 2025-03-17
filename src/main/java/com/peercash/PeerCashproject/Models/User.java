package com.peercash.PeerCashproject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String lastname;
    private String document;
    private LocalDate birth;
    private boolean active;
    private String typeUser;
    private String bankAccount;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
