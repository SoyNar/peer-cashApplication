package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment,Long> {

    List<Investment> findByInvestorId(Long investorId);
}
