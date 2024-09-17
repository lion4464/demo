package com.example.demo.load;

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
public class LoadMinDto {
    private UUID id;
    private String loadNumber;
    private String pickupAddress;
    private String deliveryAddress;
    private LoadStatus status;
    private Long createdDate;
    private Long modifiedDate;
    private UUID createdBy;
    private UUID modifiedBy;
}
