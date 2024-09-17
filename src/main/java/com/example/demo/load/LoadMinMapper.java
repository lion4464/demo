package com.example.demo.load;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoadMinMapper {
     LoadMinDto map(LoadEntity entity);

    @InheritInverseConfiguration
     LoadEntity map(LoadRequestDto dto);

    @InheritInverseConfiguration
     LoadEntity map(LoadCreateRequestDto dto);

}
