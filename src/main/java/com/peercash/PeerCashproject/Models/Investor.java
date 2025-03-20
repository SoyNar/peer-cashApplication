package com.peercash.PeerCashproject.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("INVESTOR")
public class Investor extends User{
    private BigDecimal availableBalance;
    private  BigDecimal totalInvested;
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Investment> investments;


}
