package com.example.demo.load;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoadRepo extends ReactiveCrudRepository<LoadEntity, UUID>,LoadCustomRepository {
    Flux<LoadEntity> findAllByDeletedFalse();

    Mono<LoadEntity> findByIdAndDeletedFalse(UUID id);



}
