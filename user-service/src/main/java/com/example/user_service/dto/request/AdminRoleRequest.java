package com.example.user_service.dto.request;

import com.example.user_service.enums.Roles;

public record AdminRoleRequest(
        Long userId,
        Roles role
) {


}
