
package com.example.authentication_service.controller;

import com.example.authentication_service.dto.request.AuthenticationRequestDto;
import com.example.authentication_service.dto.response.AuthenticationResponseDto;
import com.example.authentication_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")

    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto requestDto) {

        return ResponseEntity.ok(service.authenticate(requestDto));
    }

    // @PostMapping("/refresh-token")
    // public ResponseEntity<AuthenticationDto> refreshToken(@RequestParam Long
    // refreshToken) {
    // AuthenticationDto response = service.refreshToken(refreshToken);
    // return ResponseEntity.ok(response);
    // }
    //
    // @PostMapping("/logout")
    // public ResponseEntity<Void> revokeToken(@RequestParam Long refreshToken) {
    // service.revokeRefreshToken(refreshToken);
    // return ResponseEntity.noContent().build();
    // }

}
