package com.example.authentication_service.dto;

public record RegistrationDto(
        String username,
        String email,
        String password,
        String role) {
}
