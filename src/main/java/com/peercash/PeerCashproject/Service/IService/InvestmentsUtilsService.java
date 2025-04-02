package com.peercash.PeerCashproject.Service.IService;

import java.math.BigDecimal;

public interface InvestmentsUtilsService {

    BigDecimal calculateTotalWithInterest(BigDecimal amount, BigDecimal interestRate, int months);
}
