package com.example.authentication_service.service;

import com.example.authentication_service.model.User;
import com.example.authentication_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.GONE ,"The user have been delete or inactive ")
        );


    }




}
