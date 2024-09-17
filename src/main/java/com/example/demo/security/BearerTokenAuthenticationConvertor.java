package com.example.demo.security;

import com.example.demo.exception.UnAuthorizeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class BearerTokenAuthenticationConvertor implements ServerAuthenticationConverter {
    private final JwtHandler jwtHandler;
    private final static String BEARER_PREFIX = "Bearer ";
    private final static Function<String, Mono<String>> getBearerValue = authValue -> {
        if (authValue == null || !authValue.startsWith(BEARER_PREFIX)) {
            return Mono.error(new UnAuthorizeException("Token is required"));
        }

        String token = authValue.substring(BEARER_PREFIX.length());
        if (token.isEmpty()) {
            return Mono.error(new UnAuthorizeException("Token is required"));
        }

        return Mono.just(token);
    };

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractHeader(exchange)
                .flatMap(getBearerValue)
                .flatMap(jwtHandler::check)
                .flatMap(UserAuthenticationBearer::create)
                .flatMap(auth -> Mono.deferContextual(ctx -> Mono.just(ReactiveSecurityContextHolder.withAuthentication(auth)))
                        .thenReturn(auth));

    }

    private Mono<String> extractHeader(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION));
    }
}
