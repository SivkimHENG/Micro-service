package com.example.user_service.service;

import com.example.user_service.dto.*;
import com.example.user_service.enums.Roles;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDto register(RegisterRequestDto registerRequestDto){

        if(userRepository.existsByEmail(registerRequestDto.email())){
            throw new RuntimeException("Email already existed"+ registerRequestDto.email());
        }

        if(userRepository.existsByUsername(registerRequestDto.username())) {
            throw new RuntimeException("Username already existed"+ registerRequestDto.username());
        }

        User user = new User();
        user.setEmail(registerRequestDto.email());
        user.setUsername(registerRequestDto.username());
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));


        if(registerRequestDto.roles() != null && !registerRequestDto.roles().isEmpty()){
            user.setRoles(Collections.singletonList(Roles.valueOf(registerRequestDto.roles().get(0).name())));
        } else {
            user.setRoles(Collections.singletonList(Roles.CUSTOMER));
        }

        user.setEnabled(true);

        User registeredUser = userRepository.save(user);

        String accessToken = jwtTokenService.generateAccessToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(user) ;
        return new UserDto(registeredUser.getUsername(),accessToken,refreshToken);
    }

    @Override
    public UserDto register(User user) {
        List<Roles> rolesList = List.of(Roles.valueOf(user.getRoles().toString()));
        RegisterRequestDto registerRequestDto = new RegisterRequestDto(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                rolesList
        );

        return register(registerRequestDto);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.username(),
                        authRequestDto.password()
                ));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        String refreshToken = jwtTokenService.generateRefreshToken(userDetails);

        return new AuthResponseDto(accessToken,refreshToken);
    }

    @Override
    public AuthResponseDto refreshToken(AuthRequestDto authRequestDto) {
        return refreshToken(new RefreshTokenRequestDto(authRequestDto.refreshToken()));
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.refreshToken();
        if(refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new RuntimeException("Refresh Token is required");
        }

        String username = jwtTokenService.getUsernameFromToken(refreshToken);
        User user = (User) userDetailsService.loadUserByUsername(username);

        if(!jwtTokenService.isValidRefreshToken(refreshToken,username)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String newAccessToken = jwtTokenService.generateAccessToken(user);
        String newRefreshToken = jwtTokenService.generateRefreshToken(user);

        return new AuthResponseDto(newAccessToken,newRefreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserDetails(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));

        String accessToken = jwtTokenService.generateAccessToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(user);

        return new UserDto(user.getUsername(),accessToken,refreshToken);
    }

    @Override
    public void changePassword(String username, ChangePasswordDto changePasswordDto) {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found" + username));

        if(!passwordEncoder.matches(changePasswordDto.currentPassword(), user.getPassword())){
            throw new RuntimeException("Current Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userRepository.save(user);

    }

    @Override
    public void logout(String accessToken) {
        String username  = jwtTokenService.getUsernameFromToken(accessToken);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {

        String username = jwtTokenService.getUsernameFromToken(token);
        return jwtTokenService.isValidRefreshToken(token,username);
    }





}
