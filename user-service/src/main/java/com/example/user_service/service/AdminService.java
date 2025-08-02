package com.example.user_service.service;

import com.example.user_service.dto.*;
import com.example.user_service.dto.event.UserEventDto;
import com.example.user_service.dto.request.*;
import com.example.user_service.dto.response.AuditLogResponse;
import com.example.user_service.dto.response.UserLogHistoryResponse;
import com.example.user_service.dto.response.UserResponseDto;
import com.example.user_service.dto.response.UserRoleResponse;
import com.example.user_service.enums.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AdminService {


    UserRoleResponse assignRole(AdminRoleRequest adminRoleRequest);
    UserRoleResponse removeRole(AdminRemoveRoleRequest adminRemoveRoleRequest);
    UserRoleResponse getUserRoles(String username);

    UserResponseDto createUser(AdminCreateRequest createRequest);
    UserResponseDto updateUser(Long userId,AdminUpdateRequest updateRequest);

    void  deactivateUser(Long userId);
    UserResponseDto reactivateUser(Long userId, AdminReactiveRequest adminReactiveRequest);


    List<AuditLogResponse> getAuditLog(int page, int size);
    Page<UserLogHistoryResponse> getUserLoginHistory(Long userId, int page, int size);


    private void logAuditAction(String actions, String username, String targetUser, String details) { }


}
