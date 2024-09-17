package com.example.demo.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private UUID userId;
    private String token;
    private Date createdAt;
    private Date expiredAt;
}
