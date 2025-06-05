package com.example.authentication_service.Mapper;


import com.example.authentication_service.dto.ProfileDto;
import com.example.authentication_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public ProfileDto toUserProfileDto(final User user) {
        return new ProfileDto(user.getEmail(), user.getUsername());
    }

}
