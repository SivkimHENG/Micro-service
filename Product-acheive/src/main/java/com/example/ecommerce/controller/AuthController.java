package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticateRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.AuthenticateResponse;
import com.example.ecommerce.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateService service;

    @PostMapping("/register")
    
    public ResponseEntity<AuthenticateResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> register(
            @RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(service.authenticate(request));

    }

}
