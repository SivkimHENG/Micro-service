package com.example.user_service.dto;

import com.example.user_service.enums.Roles;

import java.util.List;

public record RegisterRequestDto(
        String username,
        String email,
        String password,
       List<Roles> roles
) {
}
