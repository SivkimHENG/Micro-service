package com.example.user_service.dto;

public record AuthRequestDto(
        String username,
        String password,
        String refreshToken
) {
}
