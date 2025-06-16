package com.example.authentication_service.controller;

import com.example.authentication_service.Mapper.UserRegistrationMapper;
import com.example.authentication_service.dto.RegistrationDto;
import com.example.authentication_service.dto.request.RegistrationRequestDto;
import com.example.authentication_service.dto.response.RegistrationResponseDto;
import com.example.authentication_service.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class RegistrationController {

    private final UserRegistrationService service;
    private final UserRegistrationMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody final RegistrationRequestDto requestDto) {
        final var registeredUser = service.registerUser(mapper.toEntity(requestDto));

        return ResponseEntity.ok(mapper.toRegistrationDto(registeredUser));
    }

}
