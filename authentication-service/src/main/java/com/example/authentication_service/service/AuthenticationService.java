package com.example.authentication_service.service;

import com.example.authentication_service.dto.AuthenticationDto;
import com.example.authentication_service.dto.request.AuthenticationRequestDto;
import com.example.authentication_service.dto.response.AuthenticationResponseDto;
import com.example.authentication_service.model.RefreshToken;
import com.example.authentication_service.model.User;
import com.example.authentication_service.repository.RefreshTokenRepository;
import com.example.authentication_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Duration refreshTokenTtl;

    public AuthenticationResponseDto authenticationResponseDto(
            final AuthenticationRequestDto request) {
        var authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
        var authentication = authenticationManager.authenticate(authToken);

        var token = jwtService.generateToken(request.username());

        return new AuthenticationResponseDto(token);
    }

    public void revokeRefreshToken(Long refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

}
