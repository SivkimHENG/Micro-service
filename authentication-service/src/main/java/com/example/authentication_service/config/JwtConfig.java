package com.example.authentication_service.config;

import com.example.authentication_service.service.JwtService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import org.hibernate.annotations.LazyGroup;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private Duration ttl;
    private String name;

    @Bean
    @Lazy
    public JwtEncoder jwtEncoder() {
        final var jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();

        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    @Bean
    @Lazy
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    @Lazy
    public JwtService jwtService(
            @Value("${spring.application.name}") final String appName,
            JwtEncoder jwtEncoder) {
        return new JwtService(appName, ttl, jwtEncoder);

    }

}
