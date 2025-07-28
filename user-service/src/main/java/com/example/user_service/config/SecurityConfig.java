package com.example.user_service.config;

import com.example.user_service.security.JwtFilter;
import com.example.user_service.security.JwtTokenService;
import com.example.user_service.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.stream.LongStream;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfig {


    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/v1/api/auth/**").permitAll()
                                        .requestMatchers("/v1/api/public/**").permitAll()
                                        .requestMatchers("/v1/api/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/v1/api/customer/**").hasAnyRole("CUSTOMER")
                                        .requestMatchers("/v1/api/moderator/**").hasRole("MODERATOR")
                                        .requestMatchers("/v1/api/vendor/**").hasRole("VENDOR")
                                        .requestMatchers("/v1/api/users/**").hasAnyRole("ADMIN", "MODERATOR")
                                        .requestMatchers("/v1/api/profile/**")
                                        .authenticated()
                                        .anyRequest()
                                        .authenticated())
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable()).build();

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration  = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("localhost:8081","localhost:8082"));
        configuration.setAllowedMethods(List.of("GET","PUT","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }





    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtTokenService,userDetailsService);
    }


}
