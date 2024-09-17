package com.example.demo.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity entity);

    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}
