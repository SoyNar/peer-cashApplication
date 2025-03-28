package com.peercash.PeerCashproject.Models;

import com.peercash.PeerCashproject.Enums.StatusT;
import com.peercash.PeerCashproject.Enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions" )
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime dateTransaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusT statusTransaction;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String reference;

    private String paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
