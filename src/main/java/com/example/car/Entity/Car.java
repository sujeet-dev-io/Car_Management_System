package com.example.car.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Car {

    @Id
    private String carId;
    private String name;
    private String model;
    private Integer year;
    private Double price;
    private String color;
    private String fuelType;
    private LocalDateTime createDate;
    private Boolean deleted = Boolean.FALSE;

}
