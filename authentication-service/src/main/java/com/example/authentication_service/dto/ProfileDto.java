package com.example.authentication_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@RequiredArgsConstructor
public class ProfileDto {
    private String email;
    private String username;


    public ProfileDto(String email, String username) { }


}
