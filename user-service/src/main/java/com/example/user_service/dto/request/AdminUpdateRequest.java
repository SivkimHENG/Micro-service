package com.example.user_service.dto.request;

public record AdminUpdateRequest(
        String username,
        boolean isActive
) {
}
