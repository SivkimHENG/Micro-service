package com.example.authentication_service.controller;


import com.example.authentication_service.Mapper.UserRegistrationMapper;
import com.example.authentication_service.dto.RegistrationDto;
import com.example.authentication_service.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
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
    public RegistrationDto registerUser(@RequestBody final RegistrationDto dto) {
        final var registeredUser = service.registerUser(mapper.toEntity(dto));


        return ResponseEntity.ok(mapper.toRegistrationDto(registeredUser)).getBody();
    }


}
