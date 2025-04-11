package com.peercash.PeerCashproject.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tokens")
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token",unique = true,nullable = false)
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private boolean isRevoked;
    private  boolean isExpired;
    private Date expirationTime;
}
