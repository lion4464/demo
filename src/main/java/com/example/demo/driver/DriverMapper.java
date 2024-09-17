package com.example.demo.driver;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDto map(DriverEntity entity);

    @InheritInverseConfiguration
    DriverEntity map(DriverDto dto);

}
