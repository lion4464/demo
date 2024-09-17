package com.example.demo.load;

import com.example.demo.generic.SearchCriteria;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;

import java.util.List;

public interface LoadCustomRepository {
    Flux<LoadFullDto> findAllLoadWithDriver(PageRequest pageRequest, List<SearchCriteria> searchCriteria);
}

