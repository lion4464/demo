package com.example.demo.security;

import com.example.demo.exception.UnAuthorizeException;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepo;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final UserService userService;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getUserId())
                .filter(UserEntity::getEnabled)
                .switchIfEmpty(Mono.error(new UnAuthorizeException("User disabled")))
                .map(user -> {
                    SecurityContext context = new SecurityContextImpl(authentication);
                    return authentication;
                })
                .flatMap(auth -> Mono.deferContextual(ctx -> Mono.just(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                        .thenReturn(auth));

    }
}
