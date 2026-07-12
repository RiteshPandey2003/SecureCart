package com.example.authService.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final SecretKey key;

    private final long expireIN = 15 * 60 * 1000; // 15 minutes

    public JWTTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateJWTToken(Authentication auth) {
        JWTUserDetails jwtUserDetails = (JWTUserDetails) auth.getPrincipal();
        Date expireDate = new Date(System.currentTimeMillis() + expireIN);

        String token = Jwts.builder()
                .subject(Long.toString(jwtUserDetails.getId()))
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key)
                .compact();

        System.out.println("JWTTokenProvider: generated token (length=" + token.length() + ") for userId=" + jwtUserDetails.getId());
        return token;
    }
}