package com.example.Order.exception;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String,String> error = new HashMap<>();
        error.put("error",ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }





}
