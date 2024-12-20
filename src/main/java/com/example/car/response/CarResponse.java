package com.example.car.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {

    private String carId;
    private String name;
    private String model;
    private Integer year;
    private Double price;
    private String color;
    private String fuelType;
    private LocalDateTime createDate;

}
