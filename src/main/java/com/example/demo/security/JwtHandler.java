package com.example.demo.security;

import com.example.demo.exception.AuthException;
import com.example.demo.exception.UnAuthorizeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

public class JwtHandler {
    private final String secret;

    public JwtHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(String accessToken){
    return Mono.just(verify(accessToken)).onErrorResume(e ->Mono.error(new UnAuthorizeException(e.getMessage())));
    }
    private  VerificationResult verify(String token){
        Claims claims = getClaimnFromToken(token);
        final Date expirationDate = claims.getExpiration();
        if (expirationDate.before(new Date())){
            throw new RuntimeException("");
        }
        return  new VerificationResult(claims,token);
    }
    private Claims getClaimnFromToken(String token){
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    public static class  VerificationResult{
        public Claims claims;
        public  String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
