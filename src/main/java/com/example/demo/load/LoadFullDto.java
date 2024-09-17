package com.example.demo.load;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadFullDto {
    private String driverName;
    private String driverSurname;
    private String driverEmail;
    private String driverPhone;
    private String loadNumber;
    private String pickUpAddress;
    private String deliveryAddress;
    private String loadStatus;
}
