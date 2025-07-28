package com.example.user_service.dto;

public record ChangePasswordDto(
        String currentPassword,
        String newPassword,
        String confirmPassword
) { }
