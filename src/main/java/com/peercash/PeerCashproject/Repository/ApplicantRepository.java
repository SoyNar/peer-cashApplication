package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository  extends JpaRepository<Applicant,Long> {

    Optional<Applicant> findById(Long id);
    @Query("SELECT a FROM Applicant a JOIN a.roles r WHERE r.name = :roleName AND a.id = :userId")
    Optional<Applicant> findByRoleName(@Param("roleName") String roleName,@Param("userId") Long userId);

}
