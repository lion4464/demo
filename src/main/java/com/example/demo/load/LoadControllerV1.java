package com.example.demo.load;

import com.example.demo.auth.Path;
import com.example.demo.generic.PageableRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
@RequestMapping(Path.baseUrl+"load")
@PreAuthorize("hasAnyAuthority('USER')")
@Validated
public class LoadControllerV1 {
    private final LoadService loadService;
    private final LoadMinMapper loadMinMapper;

    @PostMapping("/save")
    public Mono<ResponseEntity<LoadMinDto>> save(@RequestBody @Valid LoadCreateRequestDto request){
        LoadEntity entity = loadMinMapper.map(request);
        return loadService.save(entity)
                .map(loadEntity ->  new ResponseEntity<>(loadMinMapper.map(loadEntity), HttpStatus.CREATED));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<LoadMinDto>> get(@PathVariable UUID id) {
        return loadService.getById(id)
                .map(entity -> ResponseEntity.ok(loadMinMapper.map(entity)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PatchMapping("/set-driver")
    public Mono<ResponseEntity<LoadMinDto>> setDriver(@RequestBody LoadSetDriverRequest request) {
        return loadService.setDriverToLoad(request)
                .map(entity -> ResponseEntity.ok(loadMinMapper.map(entity)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<LoadMinDto>> update(@PathVariable UUID id,
                                                          @RequestBody @Valid LoadRequestDto request) {
         return loadService.updateById(id,request).map(entity -> new ResponseEntity<>(loadMinMapper.map(entity), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return loadService.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/findAll")
    public Flux<LoadMinDto> getAll() {
        return loadService.findAll().map(loadMinMapper::map);
    }

    @PostMapping("/pageable")
    public Mono<Page<LoadFullDto>> findAllPageable(@RequestBody PageableRequest pageable) {

        PageRequest pageRequest = PageRequest.of(
                pageable.getPage(),
                pageable.getPerPage(),
                Sort.Direction.fromString(pageable.getSort().getDirection()),
                pageable.getSort().getName()
        );
        return loadService.findAllLoadWithDriver(pageRequest,pageable.getSearch());
    }


}
