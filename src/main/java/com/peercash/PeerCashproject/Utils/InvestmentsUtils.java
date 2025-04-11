package com.peercash.PeerCashproject.Utils;

import com.peercash.PeerCashproject.Service.IService.InvestmentsUtilsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class InvestmentsUtils implements InvestmentsUtilsService {

    @Override
    public BigDecimal calculateTotalWithInterest(BigDecimal amount, BigDecimal interestRate, int months) {
        BigDecimal interestFactor = BigDecimal.ONE.add(interestRate).pow(months);
        return amount.multiply(interestFactor).setScale(2, RoundingMode.HALF_UP);
    }

}
