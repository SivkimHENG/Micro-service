package com.example.user_service.dto.request;

public record AdminReactiveRequest(
        boolean active,
        Long userId
) {
}
