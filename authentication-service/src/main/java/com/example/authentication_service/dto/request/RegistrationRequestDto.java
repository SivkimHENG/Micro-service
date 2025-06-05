package com.example.authentication_service.dto.request;

public record RegistrationRequestDto(
        String email,
        String username,
        String password) {

}
