package com.example.user_service.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/auth/")
public class AdminController {


    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok("Admin Dashboard");
    }









}
