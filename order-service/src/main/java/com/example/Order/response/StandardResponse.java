package com.example.Order.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StandardResponse {
    private String message;
    private boolean isSuccess;
    private LocalDateTime timestamp;
    private Object data;


    public static StandardResponse success(String message) {
        return StandardResponse.builder()
                .message(message)
                .isSuccess(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static StandardResponse failure(String message) {
        return StandardResponse.builder()
                .message(message)
                .isSuccess(false)
                .timestamp(LocalDateTime.now())
                .build();
    }


}
