package com.example.demo.user;

import com.example.demo.exception.DoublicateKeyException;
import com.example.demo.security.CustomEncoder;
import com.example.demo.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final CustomEncoder passwordEncoder;



    public Mono<UserEntity> signUp(UserEntity entity) {
        return userRepo.IsExistsUsername(entity.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DoublicateKeyException("Username already exists"));
                    } else {
                        UserEntity newUser = entity.toBuilder()
                                .password(passwordEncoder.encode(entity.getPassword()))
                                .role(UserRole.USER)
                                .firstName(entity.getFirstName())
                                .lastName(entity.getLastName())
                                .enabled(true)
                                .createdDate(System.currentTimeMillis())
                                .modifiedDate(System.currentTimeMillis())
                                .build();

                        return userRepo.save(newUser)
                                .doOnSuccess(user -> log.info("IN signUp - user created : {}", user));
                    }
                });
    }

    public Mono<UserEntity> getUserById(UUID id){
        return userRepo.findById(id);
    }
    public Mono<UserEntity> getByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public Mono<UserEntity> getMe(CustomPrincipal authentication) {
        return getUserById(authentication.getUserId());
    }
}
