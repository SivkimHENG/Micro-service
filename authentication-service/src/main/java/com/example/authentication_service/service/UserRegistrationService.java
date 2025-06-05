package com.example.authentication_service.service;

import com.example.authentication_service.dto.RegistrationDto;
import com.example.authentication_service.dto.request.RegistrationRequestDto;
import com.example.authentication_service.enums.UserRole;
import com.example.authentication_service.exceptions.UserAlreadyExistsException;
import com.example.authentication_service.model.User;
import com.example.authentication_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegistrationRequestDto request) {

        if (userRepository.existsByUsername(request.username()) ||
                userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Username or Email already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(UserRole.USER));

        return userRepository.save(user);
    }

}
