package com.example.user_service.dto.request;

import com.example.user_service.enums.Roles;

public record AdminRemoveRoleRequest(
        Long userId,
        Roles role
) {
}
