package com.example.car.service;


import com.example.car.Dto.CarRequest;
import com.example.car.Entity.Car;
import com.example.car.response.CarResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CarService {

    CarResponse saveCarDetails(CarRequest request);

    CarResponse getCarById(String carId);

    List<CarResponse> getAllCarDetails();

    Page<CarResponse> getCarByPaginationAndSorting(int offset, int pageSize, String field, String order);

    CarResponse updateCarById(String carId, CarRequest request);

    String SoftDeleteCarById(String carId);

    String HardDeleteCarById(String carId);

    /**
     * Get a list of filtered cars based on name, model, and year,
     * with sorting options.
     *
     * @param name      the name of the car to filter (optional)
     * @param model     the model of the car to filter (optional)
     * @param year      the year of the car to filter (optional)
     * @param sortBy    the field to sort by (e.g., "name", "year", "price")
     * @param ascending true for ascending order, false for descending
     * @return a list of cars matching the criteria and sorted as specified
     */
    List<Car> getFilteredCars(String name, String model, Integer year, String sortBy, boolean ascending);

    Map<String, Object> deleteCarByName(String name);

    ResponseEntity<Map<String, Object>> globalSearch(String name, String model, Integer year, String color, String fuelType);


}
