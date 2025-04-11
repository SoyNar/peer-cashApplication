package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Role;
import com.peercash.PeerCashproject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName (String name);
}
