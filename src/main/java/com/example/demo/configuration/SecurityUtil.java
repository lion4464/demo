package com.example.demo.configuration;

import com.example.demo.security.CustomPrincipal;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.user.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class SecurityUtil {
    public static Mono<UUID> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication())
                .flatMap(authentication -> {
                    Object principal = authentication.getPrincipal();

                    if (principal instanceof CustomPrincipal user) {
                        return Mono.just(user.getUserId());
                    } else if (principal instanceof UserEntity) {
                        UserEntity userDetails = (UserEntity) principal;
                        return Mono.just(userDetails.getId());
                    }

                    return Mono.empty();
                });
    }
}
