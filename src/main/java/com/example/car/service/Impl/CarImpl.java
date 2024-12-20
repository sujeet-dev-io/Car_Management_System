package com.example.car.service.Impl;

import com.example.car.Dto.CarRequest;
import com.example.car.Entity.Car;
import com.example.car.Exception.BadRequestException;
import com.example.car.Exception.CarNotFoundException;
import com.example.car.Exception.NotFoundException;
import com.example.car.Repository.CarRepository;
import com.example.car.filter.CarFilter;
import com.example.car.response.CarResponse;
import com.example.car.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarImpl implements CarService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarRepository repository;

    @Override
    public CarResponse saveCarDetails(CarRequest request) {

        validateCarRequest(request);
        Car car = modelMapper.map(request, Car.class);
        String carId = UUID.randomUUID().toString();
        car.setCarId(carId);
        car.setCreateDate(LocalDateTime.now());

        Car savedCar;
        try {
            savedCar = repository.save(car);
        } catch (DataAccessException ex) {
            log.error("Error occurred while saving car details: {}", request, ex);
            throw new RuntimeException("Failed to save car details, please try again later.");
        }

        log.info("Car details saved successfully: {}", savedCar);

        return modelMapper.map(savedCar, CarResponse.class);
    }

    private void validateCarRequest(CarRequest request) {
        if (request == null || request.getName() == null || request.getModel() == null) {
            throw new IllegalArgumentException("Invalid car request: Name and Model are required.");
        }
    }

    @Override
    public CarResponse getCarById(String carId) {
        Car car = repository.findById(carId)
                .orElseThrow(() -> new NotFoundException(" : Please check your database"));
        log.info("Car details retrieved successfully: {}", car);
        return modelMapper.map(car, CarResponse.class);
    }

    @Override
    public List<CarResponse> getAllCarDetails() {
        List<Car> cars = repository.findAll();
        log.info("Fetched all cars: {}", cars);
        return cars.stream()
                .map(car -> modelMapper.map(car, CarResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CarResponse> getCarByPaginationAndSorting(int offset, int pageSize, String field, String order) {
        PageRequest pageRequest = PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.valueOf(order), field));
        Page<Car> carPage = repository.findAll(pageRequest);
        List<CarResponse> bookResponse = carPage.stream().map(e -> modelMapper.map(e, CarResponse.class)).toList();
        return new PageImpl<>(bookResponse, pageRequest, carPage.getTotalElements());
    }

    @Override
    public CarResponse updateCarById(String carId, CarRequest request) {
        Car car = repository.findById(carId)
                .orElseThrow(() -> new NotFoundException(": Please check your database"));
        request.setCarId(carId);
        modelMapper.map(request, car);
        Car updatedCar = repository.save(car);
        log.info("Car details updated successfully... {}", request);
        return modelMapper.map(updatedCar, CarResponse.class);
    }

    @Override
    public String SoftDeleteCarById(String carId) {
        Car car = repository.findByCarIdAndDeletedFalse(carId).orElseThrow(() -> new NotFoundException(": Please check your database"));
        car.setDeleted(true);
        repository.save(car);
        log.info("Car details deleted successfully... {}", carId);
        return "Car details deleted successfully...";

    }

    @Override
    public String HardDeleteCarById(String carId) {
        Car car = repository.findById(carId).orElseThrow(() -> new NotFoundException(": Please check your database"));
        repository.delete(car);
        log.info("Car details hard deleted successfully... {}", carId);
        return "Car details hard deleted successfully...";
    }

    // Filter
    @Override
    public List<Car> getFilteredCars(String name, String model, Integer year, String sortBy, boolean ascending) {
        Specification<Car> specification = CarFilter.applyFilters(name, model, year);

        List<Car> cars = repository.findAll(specification);

        if (cars.isEmpty()) {
            // Build the message based on missing fields
            StringBuilder message = new StringBuilder("No cars found with the following criteria: ");
            if (name != null && !name.isEmpty()) message.append("name ");
            if (model != null && !model.isEmpty()) message.append("model ");
            if (year != null) message.append("year ");
            throw new CarNotFoundException(message.toString());
        }
        // Return the cars if found
        return cars;
    }

    // Delete  Car ByName
    @Override
    public Map<String, Object> deleteCarByName(String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Find car by name
            Car car = repository.findByName(name);
            if (car == null) {
                response.put("message", "Car with name '" + name + "' is not present.");
                response.put("status", "FAILURE");
                return response;
            }
            repository.delete(car);
            response.put("message", "Car deleted successfully.");
            response.put("status", "SUCCESS");
            return response;
        } catch (Exception e) {
            response.put("message", "An error occurred while deleting the car.");
            response.put("status", "FAILURE");
            response.put("error", e.getMessage());
            return response;
        }
    }


    // GlobalSearch Feature
    @Override
    public ResponseEntity<Map<String, Object>> globalSearch(String name, String model, Integer year, String color, String fuelType) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (name == null && model == null && year == null && color == null && fuelType == null) {
                throw new BadRequestException("At least one search field should be provided.");
            }
            List<Car> cars = repository.findCarsByMultipleCriteria(name, model, year, color, fuelType);
            if (cars.isEmpty()) {
                throw new NotFoundException("No cars found matching the search criteria.");
            }
            response.put("cars", cars);
            response.put("message", "Search completed successfully.");
            response.put("status", "SUCCESS");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadRequestException e) {
            // Handle BadRequestException (invalid input)
            response.put("message", e.getMessage());
            response.put("status", "FAILURE");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
            response.put("status", "FAILURE");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            // Handle other exceptions
            response.put("message", "An error occurred while processing the search.");
            response.put("status", "FAILURE");
            response.put("error", e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
