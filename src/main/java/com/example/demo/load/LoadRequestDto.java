package com.example.demo.load;

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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoadRequestDto {
    private UUID id;
    @NotNull
    private String loadNumber;
    private String pickupAddress;
    private String deliveryAddress;
    @NotNull
    private LoadStatus status;
    private UUID driverId;
}
