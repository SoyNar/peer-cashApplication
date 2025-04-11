package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<
        Transactions, Long> {
}
