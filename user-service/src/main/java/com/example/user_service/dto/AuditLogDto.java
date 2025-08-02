package com.example.user_service.dto;

import java.time.LocalDateTime;

public record AuditLogDto(
        String id,
        String username,
        String action,
        String details,
        LocalDateTime timestamp,
        String ipaddress,
        String useragent
) {
}
