package com.example.authentication_service.Mapper;

import com.example.authentication_service.dto.request.RegistrationRequestDto;
import com.example.authentication_service.dto.response.RegistrationResponseDto;
import com.example.authentication_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User toEntity(RegistrationRequestDto registrationRequestDto) {

        final var user = new User();

        user.setEmail(registrationRequestDto.email());
        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());

        return user;

    }

    public RegistrationResponseDto registrationResponseDto(final User user) {

        return new RegistrationResponseDto(user.getEmail(), user.getUsername());
    }

}
