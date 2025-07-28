package com.example.user_service.controller;


import com.example.user_service.dto.*;
import com.example.user_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.netty.http.server.HttpServerFormDecoderProvider;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto response = authService.login(authRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        AuthResponseDto response = authService.refreshToken(refreshTokenRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        UserDto response = authService.register(registerRequestDto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/profile")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {

        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UserDto userDto = authService.getUserDetails(username);
        return ResponseEntity.ok(userDto);

    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordDto changePasswordDto,
            Authentication authentication

    ) {

        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username  = authentication.getName();
        if(!changePasswordDto.newPassword().equals(changePasswordDto.confirmPassword())) {
            return ResponseEntity.badRequest()
                    .body("new password and confirmation do not match ");
        }
        authService.changePassword(username,changePasswordDto);
        return ResponseEntity.ok("Password changed successfully");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest request) {
        String authHeader =  request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.logout(token);
        }

        return ResponseEntity.ok("Logged out");
    }


    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null && authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("No token Provided");
        }

        String token = authHeader.substring(7);
        boolean isValid = authService.validateToken(token);

        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token is invalid or expired");
        }

    }
}
