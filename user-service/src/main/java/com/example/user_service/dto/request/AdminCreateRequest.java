package com.example.user_service.dto.request;

import com.example.user_service.enums.Roles;

import java.util.List;

public record AdminCreateRequest(
        String username,
        String email,
        String password,
        List<Roles> initialRole,
        String welcomeEmail
) {
}
