package com.example.car.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    private String carId;

    @NotBlank(message = "Car name must not be blank")
    private String name;

    @NotBlank(message = "Car model must not be blank")
    private String model;

    @NotNull(message = "Year must not be null")
    @Min(value = 1886, message = "Year must be greater than or equal to 1886") // First car invention year
    private Integer year;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotBlank(message = "Color must not be blank")
    private String color;

    @NotBlank(message = "Fuel type must not be blank")
    private String fuelType;

    private LocalDateTime createDate;

    private Boolean deleted = Boolean.FALSE;
}
