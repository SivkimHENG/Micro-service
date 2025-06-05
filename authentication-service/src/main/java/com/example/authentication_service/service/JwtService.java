package com.example.authentication_service.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class JwtService {

    private String issuer;
    private Duration ttl;
    private JwtEncoder jwtEncoder;

    public String generateToken(String username) {
        var claimsSet = JwtClaimsSet.builder()
                .subject(username).issuer(issuer).expiresAt(Instant.now().plus(ttl)).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

}
