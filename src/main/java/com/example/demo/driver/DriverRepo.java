package com.example.demo.driver;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DriverRepo extends ReactiveCrudRepository<DriverEntity, UUID> {
    Mono<DriverEntity> findByIdAndDeletedFalse(UUID id);

    Flux<DriverEntity> findAllByDeletedFalse();

    @Query("SELECT COUNT(id) > 0 FROM drivers WHERE email = :email AND deleted = false")
    Mono<Boolean> IsExistsEmail(String email);

    @Query("SELECT COUNT(id) > 0 FROM drivers WHERE id = :id AND deleted = false")
    Mono<Boolean> isExsistsIdAndDeletedFalse(UUID id);
}
