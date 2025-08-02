package com.example.user_service.dto.response;

import java.time.LocalDateTime;

public record UserLogHistoryResponse(
         Long id,
         String username,
         String ipAddress,
         String userAgent,
         LocalDateTime loginTime,
         LocalDateTime logoutTime,
         boolean successful
) {
}
