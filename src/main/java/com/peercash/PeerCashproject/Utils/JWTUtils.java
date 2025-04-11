package com.peercash.PeerCashproject.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JWTUtils {

    private  final Key key;

    private  final long accesTokenExpiration;

    private final long refreshTokenExpiration;

    public JWTUtils(
            @Value(value = "${jwt.access-toke-expiration}")
            long accesTokenExpiration,
            @Value("${jwt.refresh-expiration}")
            long refreshTokenExpiration,
            @Value("${jwt.secret}")
            String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.accesTokenExpiration = accesTokenExpiration;

    }
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(String email, long expiresIn) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, refreshTokenExpiration);
    }
    public String generateAccessToken(String email) {
        return generateToken(email,accesTokenExpiration);
    }

    private Key getSigninKey() {
        return this.key;
    }


}
