package com.example.user_service.security;

import com.example.user_service.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.sound.midi.SysexMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.access-token-expiration}")
    private Long accessTokenExpiration;
    @Value("${security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;



    public String generateAccessToken(UserDetails user) {
        Map<String,Object> claims = new HashMap<>();
        if(user instanceof User) {
            claims.put("roles", ((User) user).getRoles());
        } else {
            claims.put("roles",user.getAuthorities());
        }
        return createToken(claims,user.getUsername(),accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails user) {
        return createToken(new HashMap<>(), user.getUsername(), refreshTokenExpiration);
    }

    public boolean isValidAccessToken(String token, String username) {
        try {
            String usernameToken = getUsernameFromToken(token);
            return usernameToken.equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex){
            return false;
        }
    }

    public boolean isValidRefreshToken(String token, String username) {
        try {
            String usernameToken = getUsernameFromToken(token);
            return usernameToken.equals(username) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    

    public String getUsernameFromToken(String token) {
        return extractClaims(token,Claims::getSubject);
    }
    private  boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims,T> resolver){
        Claims claims =  extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }






}
