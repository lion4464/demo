package com.example.demo.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
@Builder(toBuilder = true)
public class UserEntity {
    @Id
    private UUID id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private UserRole role;

    private Boolean enabled;

    private long createdDate;
    private long modifiedDate;

}
