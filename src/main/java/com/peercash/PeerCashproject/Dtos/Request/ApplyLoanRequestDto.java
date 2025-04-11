package com.peercash.PeerCashproject.Dtos.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLoanRequestDto {

    @NotNull(message = "No puede estar vacio")
    private String reason;
    @NotNull(message = "Te falta el valor de tu prestamos")
    private BigDecimal amount;
    private int numberOfInstallments;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
}
