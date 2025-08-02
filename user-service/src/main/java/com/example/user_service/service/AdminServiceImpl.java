package com.example.user_service.service;


import com.example.user_service.dto.*;
import com.example.user_service.dto.event.UserEventDto;
import com.example.user_service.dto.request.*;
import com.example.user_service.dto.response.AdminRemoveRoleResponse;
import com.example.user_service.dto.response.UserLogHistoryResponse;
import com.example.user_service.dto.response.UserResponseDto;
import com.example.user_service.dto.response.UserRoleResponse;
import com.example.user_service.enums.Roles;
import com.example.user_service.exception.RoleExceptionHandler;
import com.example.user_service.model.AuditLog;
import com.example.user_service.model.User;
import com.example.user_service.repository.AuditLogRepository;
import com.example.user_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service

public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public UserRoleResponse assignRole(AdminRoleRequest adminRoleRequest) {
        User user = userRepository.findById(adminRoleRequest.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.getRoles().contains(Roles.MODERATOR)){
            user.getRoles().add(Roles.MODERATOR);
            userRepository.save(user);
        } else {
            throw new RoleExceptionHandler(Roles.MODERATOR.toString());
        }
        logAuditAction("ASSIGN_ROLE", getCurrentUsername(),user.getUsername(),"Role assigned successfully" + user.getRoles());

        return modelMapper.map(user, UserRoleResponse.class);
    }

    @Override
    public UserRoleResponse removeRole(AdminRemoveRoleRequest adminRemoveRoleRequest) {
        User user = userRepository.findById(adminRemoveRoleRequest.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.getRoles().contains(Roles.ADMIN)){
            user.getRoles().remove(adminRemoveRoleRequest.role());
            userRepository.save(user);
        } else {
            throw new RoleExceptionHandler(Roles.ADMIN.toString());
        }

        logAuditAction("ASSIGN_ROLE", getCurrentUsername(),user.getUsername(),"Role removed successfully" + user.getRoles());

        return modelMapper.map(user,UserRoleResponse.class);
    }

    @Override
    public UserRoleResponse getUserRoles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserRoleResponse.class);
    }


    @Override
    public UserResponseDto createUser(AdminCreateRequest createRequest) {
        if(userRepository.existsByUsername(createRequest.username())) {
            throw new UsernameNotFoundException("User not Found");
        }

        if(userRepository.existsByEmail(createRequest.email())) {
            throw new UsernameNotFoundException("User not Found");
        }

        User user  = new User();
        user.setPassword(passwordEncoder.encode(createRequest.password()));
        user.setCreatedAt(LocalDateTime.now());

        User createdUser = userRepository.save(user);

        logAuditAction("CREATE_USER", getCurrentUsername(), createdUser.getUsername(),
                "User created with email" + createdUser.getEmail()
                );
        return modelMapper.map(user, UserResponseDto.class);
    }

    /**
     * @param userId
     * @param adminUpdateRequest
     * @return modelMapper
     */
    @Override
    public UserResponseDto updateUser(Long userId, AdminUpdateRequest adminUpdateRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        modelMapper.map(adminUpdateRequest,user);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        logAuditAction("UPDATE_USER", getCurrentUsername(), updatedUser.getUsername(),
                "User updated");


        return modelMapper.map(user,UserResponseDto.class);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public void deactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * @param userId
     * @param adminReactiveRequest
     * @return
     */

    @Override
    public UserResponseDto reactivateUser(Long userId, AdminReactiveRequest adminReactiveRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.isActive()) {
            throw new RuntimeException("User is already active ");
        }

        if(!adminReactiveRequest.active()) {
            throw new RuntimeException("Reactivation request must set 'active' to true");
        }
        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }
    /**
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<AuditLogResponse> getAuditLog(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<AuditLog> auditLogs = auditLogRepository.findAll(pageable);

        return auditLogs.getContent().stream()
                .map(auditLog ->
                        modelMapper.map(auditLog, AuditLogResponse.class))
                .toList();
    }

    @Override
    public Page<UserLogHistoryResponse> getUserLoginHistory(Long userId,int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        Pageable pageable = PageRequest.of(page,size, Sort.by("loginTime").descending());
    }



    private void logAuditAction(String actions, String username,String targetUser,String details){
       AuditLog auditLog = new AuditLog() ;
       auditLog.setAction(actions);
       auditLog.setUsername(username);
       auditLog.setTargetUser(targetUser);
       auditLog.setDetails(details);

       auditLogRepository.save(auditLog);
    }


    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "system";
    }

}
