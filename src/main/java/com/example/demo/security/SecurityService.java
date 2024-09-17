package com.example.demo.security;

import com.example.demo.exception.AuthException;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepo;
import com.example.demo.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import com.example.demo.security.CustomEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final  CustomEncoder passwordEncoder;
    private final TokenDetail tokenDetail;


private AuthResponse doGenerateToken(UserEntity user){
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRole());
    claims.put("username",user.getUsername());
    return doGenerateToken(claims, user.getId().toString(), tokenDetail.getAccessExpirationTime());
}
    private AuthResponse doGenerateToken(Map<String, Object> claims, String username, String expirationTime) {
        long expirationTimeLong = Long.parseLong(expirationTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
        SecretKey key = Keys.hmacShaKeyFor(tokenDetail.getSecret().getBytes());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
        return AuthResponse.builder()
                 .token(token)
                 .userId(UUID.fromString(username))
                .createdAt(createdDate)
                .expiredAt(expirationDate).build();
    }


    public Mono<AuthResponse> authenticate(String username,String password){
        return userService.getByUsername(username)
                .flatMap(user -> {
                        if (!user.getEnabled()){
                            Mono.error(new AuthException("account disabled","USER_ACCOUNT_DISABLED"));
                        }
                        if (!passwordEncoder.matches(password,user.getPassword())){
                            Mono.error(new AuthException("password is wrong","PASSWORD_IS_WRONG"));
                        }
                        return Mono.just(doGenerateToken(user));
                })
                .switchIfEmpty(Mono.error(new AuthException("username not found","USERNAME_NOT_FOUND")));
    }

}
