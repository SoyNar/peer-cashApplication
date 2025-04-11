package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITokenRepository extends JpaRepository<Tokens,Long> {
    List<Tokens> findAllIExpiredIsFalseOrRevokedIsFalseByUserId (Long userId);
}
