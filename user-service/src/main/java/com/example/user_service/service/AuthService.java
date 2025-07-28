package com.example.user_service.service;

import com.example.user_service.dto.*;
import com.example.user_service.model.User;
import org.springframework.stereotype.Service;
@Service
public interface AuthService {

    public AuthResponseDto login(AuthRequestDto authRequestDto);
    public AuthResponseDto refreshToken(AuthRequestDto authRequestDto);
    public AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    public UserDto register(RegisterRequestDto registerRequestDto);
    public UserDto register(User user);
    public UserDto getUserDetails(String username);
    public void changePassword(String username, ChangePasswordDto changePasswordDto);
    public void logout(String accessToken);
    public boolean validateToken(String token);




}
