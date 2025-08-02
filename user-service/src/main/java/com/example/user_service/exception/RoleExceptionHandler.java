package com.example.user_service.exception;

public class RoleExceptionHandler extends RuntimeException {

    public RoleExceptionHandler(String message) {
       super(message);
    }

    public RoleExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
