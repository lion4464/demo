package com.example.demo.load;

import com.example.demo.driver.DriverEntity;
import com.example.demo.driver.DriverService;
import com.example.demo.exception.DataNotFoundException;
import com.example.demo.generic.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoadService {
    private final LoadRepo loadRepo;
    private final DriverService driverService;

    public Mono<LoadEntity> save(LoadEntity entity) {
        return loadRepo.save(entity);
    }

    public Mono<LoadEntity> getById(UUID id) {
        return loadRepo.findByIdAndDeletedFalse(id).switchIfEmpty(Mono.error(new DataNotFoundException("Load not found")));
    }

    public Mono<LoadEntity> updateById(UUID id, LoadRequestDto request) {
        return getById(id)
                .flatMap(entity -> {
                    entity.setDeliveryAddress(request.getDeliveryAddress());
                    entity.setLoadNumber(request.getLoadNumber());
                    entity.setPickupAddress(request.getPickupAddress());
                    entity.setStatus(request.getStatus());
                    return loadRepo.save(entity);
                });
    }

    public Mono<Void> deleteById(UUID id) {
            return getById(id).flatMap(load -> {
                load.setDeleted(true);
                return loadRepo.save(load);
            }).then();
    }

    public Flux<LoadEntity> findAll() {
        return loadRepo.findAllByDeletedFalse();
    }



    public Mono<Page<LoadFullDto>> findAllLoadWithDriver(PageRequest pageRequest, List<SearchCriteria> searchCriteria) {
        return loadRepo.findAllLoadWithDriver(pageRequest, searchCriteria)
                .collectList()
                .zipWith(loadRepo.count())
                .map(tuple -> {
                    return new PageImpl<>(tuple.getT1(), pageRequest, tuple.getT2());
                });
    }
    public Mono<LoadEntity> setDriverToLoad(LoadSetDriverRequest request) {
        return driverService.isExistsById(request.getDriverId())
                .flatMap(exists -> {
                    if (exists) {
                        return getById(request.getId())
                                .flatMap(entity -> {
                                    entity.setDriverId(request.getDriverId());
                                    return loadRepo.save(entity);
                                });
                    } else {
                        return Mono.error(new DataNotFoundException("Driver not found with ID " + request.getDriverId()));
                    }
                });
    }

}
