package com.example.user_service.config;

import com.example.user_service.dto.UserDto;
import com.example.user_service.dto.response.UserResponseDto;
import com.example.user_service.enums.Roles;
import com.example.user_service.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
