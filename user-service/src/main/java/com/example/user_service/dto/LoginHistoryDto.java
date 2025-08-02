package com.example.user_service.dto;

import java.time.LocalDateTime;

public record LoginHistoryDto(
        String sessionId,
        String username,
        LocalDateTime loginTime,
        LocalDateTime logoutTime,
        String ipaddress,
        String deviceInfo,
        boolean isActive
) {
}
