package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity,Long> {
}
