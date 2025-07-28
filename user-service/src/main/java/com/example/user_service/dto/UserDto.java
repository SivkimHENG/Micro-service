package com.example.user_service.dto;

public record UserDto(
        String username,
        String refreshToken,
        String accessToken
) { }



