package com.example.authService.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProvider {

    // Must be >= 32 characters/bytes for HS256
    private final String APP_SECRET = "SecureCartJwtSigningKey2026ChangeThisInProdEnvVar";
    private final SecretKey key = Keys.hmacShaKeyFor(APP_SECRET.getBytes());

    private final long expireIN = 15 * 60 * 1000; // 15 minutes

    public String generateJWTToken(Authentication auth) {
        JWTUserDetails jwtUserDetails = (JWTUserDetails) auth.getPrincipal();
        Date expireDate = new Date(System.currentTimeMillis() + expireIN);

        return Jwts.builder()
                .subject(Long.toString(jwtUserDetails.getId()))
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }
}