package com.example.user_service.dto.response;

import com.example.user_service.enums.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        String username,
        String email,
        List<Roles> roles,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime lastLoginAt

) {
}
