package com.example.demo.driver;

import com.example.demo.auth.Path;
import com.example.demo.exception.DoublicateKeyException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping(Path.baseUrl+"driver")
@PreAuthorize("hasAnyAuthority('USER')")
@Validated
public class DriverControllerV1 {
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @PostMapping("/save")
    public Mono<ResponseEntity<DriverDto>> save(@RequestBody @Valid DriverDto request) throws DoublicateKeyException {
        DriverEntity entity = driverMapper.map(request);
        return driverService.save(entity)
                .map(driverEntity ->  new ResponseEntity<>(driverMapper.map(driverEntity), HttpStatus.CREATED));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DriverDto>> get(@PathVariable UUID id) {
        return driverService.getById(id)
                .map(entity -> ResponseEntity.ok(driverMapper.map(entity)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<DriverDto>> update(@PathVariable UUID id,
                                                  @RequestBody @Valid DriverDto request) {
        return driverService.updateById(id,request).map(entity -> new ResponseEntity<>(driverMapper.map(entity), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return driverService.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/findAll")
    public Flux<DriverDto> getAll() {
        return driverService.findAll().map(driverMapper::map);

    }
}
