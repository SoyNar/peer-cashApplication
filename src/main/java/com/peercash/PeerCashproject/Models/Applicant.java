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
@DiscriminatorValue("APPLICANT")
public class Applicant  extends  User{
    //saldo pendiente
    private BigDecimal outStandingBalance;

    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loans> loans;
}
