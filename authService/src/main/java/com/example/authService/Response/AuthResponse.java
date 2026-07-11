package com.example.authService.Response;

import lombok.Data;

@Data
public class AuthResponse {

    String message;
    Long userId;
    String refreshToken;
    String accessToken;
}
