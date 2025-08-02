package com.example.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record LoginHistoryResponseDto(
        String session,
        String username,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime loginTime,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime logoutTime,
        String ipAddress,
        String deviceInfo,
        String userAgent,
        boolean isActive
) { }
