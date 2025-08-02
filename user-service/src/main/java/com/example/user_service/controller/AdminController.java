package com.example.user_service.controller;

import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.request.*;
import com.example.user_service.dto.response.AuditLogResponse;
import com.example.user_service.dto.response.UserLogHistoryResponse;
import com.example.user_service.dto.response.UserResponseDto;
import com.example.user_service.dto.response.UserRoleResponse;
import com.example.user_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok("Admin Dashboard");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/assign")
    public ResponseEntity<UserRoleResponse> assignRole(@RequestBody AdminRoleRequest request) {
        return ResponseEntity.ok(adminService.assignRole(request));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/remove")
    public ResponseEntity<UserRoleResponse> removeRole(@RequestBody AdminRemoveRoleRequest request) {
        return ResponseEntity.ok(adminService.removeRole(request));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/{username}")
    public ResponseEntity<UserRoleResponse> getUserRole(@PathVariable String username) {
        return ResponseEntity.ok(adminService.getUserRoles(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody AdminCreateRequest request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @RequestBody AdminUpdateRequest request) {
        return ResponseEntity.ok(adminService.updateUser(userId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/user/{userId}/reactivate")
    public ResponseEntity<UserResponseDto> reactivateUser(
            @PathVariable Long userId,
            @RequestBody AdminReactiveRequest adminReactiveRequest
            ) {
        return ResponseEntity.ok(adminService.reactivateUser(userId, adminReactiveRequest));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/user/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        adminService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/audit-log")
    public ResponseEntity<List<AuditLogResponse>> getAuditLog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminService.getAuditLog(page,size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userId}/login-history")
    public ResponseEntity<Page<UserLogHistoryResponse>> getUserLoginHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(adminService.getUserLoginHistory(userId,page,size ));

    }

}
