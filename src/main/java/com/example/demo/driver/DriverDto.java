package com.example.demo.driver;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String email;
    @NotNull
    private String phone;
}
