package com.example.user_service.dto.event;

import java.time.LocalDateTime;

public record UserEventDto(
       String eventType,
       String username,
       Object eventData,
       LocalDateTime timestamp,
       String source
) {
}
