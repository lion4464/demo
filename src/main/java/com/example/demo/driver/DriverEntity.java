package com.example.demo.driver;

import com.example.demo.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("drivers")
public class DriverEntity extends BaseEntity {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Boolean deleted;
}
