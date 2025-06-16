package com.example.authentication_service.service;

import com.example.authentication_service.dto.AuthenticationResponse;
import com.example.authentication_service.model.Token;
import com.example.authentication_service.model.User;
import com.example.authentication_service.repository.TokenRepository;
import com.example.authentication_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authManager;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authManager = authManager;
    }


    public AuthenticationResponse register(User request) {
        if(userRepository.findByUsername(request.getUsername().isPresent())) {

        }


    }


}
