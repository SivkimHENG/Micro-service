package com.example.user_service.dto.response;

import com.example.user_service.enums.Roles;

public record AdminRemoveRoleResponse(
        Long userId,
        Roles role
) { }
