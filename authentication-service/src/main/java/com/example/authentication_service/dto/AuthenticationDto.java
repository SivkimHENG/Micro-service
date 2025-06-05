package com.example.authentication_service.dto;

import lombok.*;

import java.util.UUID;

@Data
@Setter
@Getter
@RequiredArgsConstructor
public record AuthenticationDto(
        String username,
        String password,
        String token,
        String refreshToken

) {}

}
