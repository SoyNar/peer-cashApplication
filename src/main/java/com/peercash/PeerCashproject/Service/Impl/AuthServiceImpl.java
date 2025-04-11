package com.peercash.PeerCashproject.Service.Impl;
import com.peercash.PeerCashproject.Dtos.Request.AuthRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.TokenResponseDto;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.Tokens;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.ITokenRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IAuthService;
import com.peercash.PeerCashproject.Utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements IAuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ITokenRepository tokenRepository;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    @Transactional
    @Override
    public TokenResponseDto authenticate(AuthRequestDto authRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword());
try{
    Authentication authentication =
            authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = this.userRepository.findByEmail(authRequestDto.getEmail()).orElseThrow(()
    -> new UserNotFondException("Usuario no encontrado"));
    revokedUsersToken(user);
   String accesToken = this.jwtUtils.generateAccessToken(authRequestDto.getEmail());
   String refreshToken = this.jwtUtils.generateRefreshToken(authRequestDto.getEmail());
   savedToken(user,accesToken);
   savedToken(user,refreshToken);
       return TokenResponseDto.builder()
            .accessToken(accesToken)
            .refreshToken(refreshToken)
            .build();

} catch (AccessDeniedException accessDeniedException){
   throw new AccessDeniedException("acceso denegado");
}
    }
    private void revokedUsersToken(User user) {
        List<Tokens> validations =
                this.tokenRepository.findAllIExpiredIsFalseOrRevokedIsFalseByUserId(user.getId());
        if (!validations.isEmpty()) {
            validations.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);
            });
            tokenRepository.saveAll(validations);
        }
    }

    private void savedToken (User user, String tokenR){
        Tokens token = Tokens.builder()
                .token(tokenR)
                .user(user)
                .isRevoked(false)
                .expirationTime(jwtUtils.extractExpiration(tokenR))
                .build();
        this.tokenRepository.save(token);
    }
}
