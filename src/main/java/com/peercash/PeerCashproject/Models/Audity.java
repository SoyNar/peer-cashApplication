package com.peercash.PeerCashproject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audity")
public class Audity {
    private Long id;
    private String nameEntity;
    private String action;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
