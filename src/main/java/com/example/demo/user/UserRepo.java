package com.example.demo.user;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepo extends R2dbcRepository<UserEntity, UUID> {
    Mono<UserEntity> findByUsername(String username);
    @Query("SELECT COUNT(id) > 0 FROM users WHERE username = :username")
    Mono<Boolean> IsExistsUsername(String username);
}
