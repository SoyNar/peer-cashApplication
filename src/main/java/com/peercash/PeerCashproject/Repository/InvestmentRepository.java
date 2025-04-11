package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment,Long> {

    @Query("SELECT i FROM Investment i WHERE i.investor.id = :investorId")
    List<Investment> findByInvestorId(@Param("investorId") Long investorId);
}
