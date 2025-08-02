package com.example.user_service.dto.response;

import com.example.user_service.enums.Roles;

public record UserRoleResponse(
        Long userId,
        String username,
        Roles role
){}
