package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Models.Role;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<Role> findByName (String name);
}
