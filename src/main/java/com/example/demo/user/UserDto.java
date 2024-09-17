package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto {
    private UUID id;
    private String username;
    private String firstName;

    private String lastName;

    private UserRole role;

    private long createdDate;
    private long modifiedDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
