package com.example.authentication_service.controller;


import com.example.authentication_service.Mapper.UserMapper;
import com.example.authentication_service.dto.ProfileDto;
import com.example.authentication_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/me")
    public ResponseEntity<ProfileDto> getUserProfile(final Authentication auth) {
        final var user = userService.getUserByUsername(auth.getName());
        return  ResponseEntity.ok(userMapper.toUserProfileDto(user));
    }




}
