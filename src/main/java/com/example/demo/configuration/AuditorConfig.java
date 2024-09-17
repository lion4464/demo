package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.util.UUID;

@Configuration
@EnableR2dbcAuditing
public class AuditorConfig {
    @Bean
    public ReactiveAuditorAware<UUID> auditorProvider() {
        return new ReactiveAuditorAwareImpl();
    }
}
