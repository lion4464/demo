package com.example.demo.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TokenDetail {
    @Value("${app.jjwt.tokenType}")
    private String tokenType;


    @Value("${app.jjwt.secret}")
    private String secret;

    @Value("${app.jjwt.access_expiration}")
    private String accessExpirationTime;

    @Value("${app.jjwt.refresh_expiration}")
    private String refreshExpirationTime;
}
