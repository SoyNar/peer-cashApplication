package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment,Long> {
}
