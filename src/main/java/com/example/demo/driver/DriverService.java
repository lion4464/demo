package com.example.demo.driver;

import com.example.demo.exception.DataNotFoundException;
import com.example.demo.exception.DoublicateKeyException;
import jakarta.validation.constraints.AssertTrue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {
    private final DriverRepo driverRepo;
    public Mono<DriverEntity> save(DriverEntity entity) {
        return driverRepo.IsExistsEmail(entity.getEmail())
                .flatMap(exists ->{
                    if (exists){
                        return Mono.error(new DoublicateKeyException("email already exists "+entity.getEmail()));
                    }else {
                        return driverRepo.save(entity);
                    }
                });
    }


    public Mono<DriverEntity> getById(UUID id) {
        return driverRepo.findByIdAndDeletedFalse(id).switchIfEmpty(Mono.error(new DataNotFoundException("data not found driverRepo")));
    }

    public Mono<Boolean> isExistsById(UUID id){
        return driverRepo.isExsistsIdAndDeletedFalse(id);
    }

    public Mono<DriverEntity> updateById(UUID id, DriverDto request) {
        return getById(id).flatMap(
                driverEntity -> {
                    driverEntity.setEmail(request.getEmail());
                    driverEntity.setName(request.getName());
                    driverEntity.setPhone(request.getPhone());
                    driverEntity.setSurname(request.getSurname());
                    return driverRepo.save(driverEntity);
                });
    }

    public Mono<Void> deleteById(UUID id) {
        return getById(id).flatMap(driver -> {
            driver.setDeleted(true);
            return driverRepo.save(driver);
        }).then();
    }

    public Flux<DriverEntity> findAll() {
        return driverRepo.findAllByDeletedFalse();
    }
}
