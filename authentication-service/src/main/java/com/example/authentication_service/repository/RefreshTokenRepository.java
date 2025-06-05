package com.example.authentication_service.repository;

import com.example.authentication_service.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByIdAndExpiresAtAfter(Long id, Instant date);
}
