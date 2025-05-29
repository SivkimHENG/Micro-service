package com.example.ecommerce.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticateResponse {
   private String token;
}
