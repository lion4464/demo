package com.example.demo.load;

import com.example.demo.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("loads")
@Builder(toBuilder = true)
public class LoadEntity extends BaseEntity {
    @Id
    private UUID id;
    private String loadNumber;
    private String pickupAddress;
    private String deliveryAddress;
    private LoadStatus status = LoadStatus.UNSIGNED;
    private UUID driverId;
    private Boolean deleted = false;
}