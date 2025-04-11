package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Models.SecurityUser;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
     private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username).orElseThrow(()
                -> new UsernameNotFoundException("uusuario no encontrado " + username));

        return new  SecurityUser(user);
    }
}
