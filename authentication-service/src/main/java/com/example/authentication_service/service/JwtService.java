package com.example.authentication_service.service;

import com.example.authentication_service.model.User;
import com.example.authentication_service.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;
    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;


    private final TokenRepository tokenRepository;


    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token){
        String username = extractUsername(token);
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());

    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        boolean validToken = tokenRepository.findByAccessToken(token).map(t -> !t.isLoggedOut()).orElse(false);
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);
        boolean validRefreshToken = tokenRepository.findByRefreshToken(token)
                .map(t -> !t.isLoggedOut()).orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;


    }



    public String generateToken(User user,long expireTime) {

        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    public String generateAccessToken(User user) {
        return generateToken(user,accessTokenExpire);
    }
    public String generateRefreshToken(User user) {
        return generateToken(user,refreshTokenExpire);
    }

    public SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
