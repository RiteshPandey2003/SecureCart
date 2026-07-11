package com.example.authService.services;
import com.example.authService.DataAccess.RefreshTokenRepository;
import com.example.authService.entities.RefreshToken;
import com.example.authService.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String CreateRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
        if (refreshToken == null) {
            refreshToken = new RefreshToken();
            refreshToken.setUserId(user.getId());
        }
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(expireSeconds));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }
}
