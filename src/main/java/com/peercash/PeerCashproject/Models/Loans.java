package com.peercash.PeerCashproject.Models;

import com.peercash.PeerCashproject.Enums.StatusT;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loan")
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal requestAmount;

    @Column(nullable = false)
    private LocalDate dateApproval;

    @Column(nullable = false)
    private StatusT statusTransaction;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private LocalDate payDay;

    @Column(nullable = false)
    private int numberOfInstallment;

    private LocalDate createAt;

    @Column(nullable = false)
    private BigDecimal platformCommission;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investment> investments;
}
