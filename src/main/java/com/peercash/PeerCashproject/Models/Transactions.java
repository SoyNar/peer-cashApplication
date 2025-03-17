package com.peercash.PeerCashproject.Models;

import com.peercash.PeerCashproject.Enums.StatusTransaction;
import com.peercash.PeerCashproject.Enums.TypeTransaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private Long id;
    private TypeTransaction typeTransaction;
    private BigDecimal amount;
    private LocalDateTime dateTransaction;
    private StatusTransaction statusTransaction;
    private String description;
    private String reference;
    private String paymentMethod;
}
