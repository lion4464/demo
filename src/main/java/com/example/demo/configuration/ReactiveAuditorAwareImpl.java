package com.example.demo.configuration;

import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class ReactiveAuditorAwareImpl implements ReactiveAuditorAware<UUID> {

    @Override
    public Mono<UUID> getCurrentAuditor() {
        return SecurityUtil.getCurrentUserId()
                .defaultIfEmpty(UUID.fromString("00000000-0000-0000-0000-000000000000"));  // agar topa olmasa
    }
}
