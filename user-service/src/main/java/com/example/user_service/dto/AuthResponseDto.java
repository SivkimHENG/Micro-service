package com.example.user_service.dto;

public record AuthResponseDto(
        String refreshToken,
        String accessToken
){ }
