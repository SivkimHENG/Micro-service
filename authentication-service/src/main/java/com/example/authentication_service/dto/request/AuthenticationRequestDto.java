package com.example.authentication_service.dto.request;

public record AuthenticationRequestDto(
        String username,
        String password) {
}
