package com.peercash.PeerCashproject.Repository;
import com.peercash.PeerCashproject.Models.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {
    @Query("SELECT i FROM Investor i JOIN i.roles r WHERE r.name = :roleName AND i.id = :userId")
    Optional<Investor> findByRoleName(@Param("roleName") String roleName, @Param("userId") Long userId);}
