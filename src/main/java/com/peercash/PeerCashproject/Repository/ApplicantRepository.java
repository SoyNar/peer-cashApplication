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
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Optional<Applicant> findByRoleName(@Param("roleName") String roleName);

}
