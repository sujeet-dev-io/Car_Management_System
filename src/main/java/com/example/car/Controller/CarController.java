package com.example.car.Controller;

import com.example.car.Dto.CarRequest;
import com.example.car.Entity.Car;
import com.example.car.Exception.CarNotFoundException;
import com.example.car.enums.Status;
import com.example.car.response.BaseResponse;
import com.example.car.response.CarResponse;
import com.example.car.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/create")
    @Operation(summary = "Create a new car", description = "Endpoint to create a new car")
    public ResponseEntity<BaseResponse<CarResponse, String>> createCar(@Valid @RequestBody CarRequest carRequest) {
        BaseResponse<CarResponse, String> response = new BaseResponse<>();
        response.setData(carService.saveCarDetails(carRequest));
        response.setStatus(Status.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get/{carId}")
    @Operation(summary = "Get car by ID", description = "Fetch details of a car using its ID")
    public ResponseEntity<BaseResponse<CarResponse, String>> getCarById(@PathVariable String carId) {
        BaseResponse<CarResponse, String> response = new BaseResponse<>();
        response.setData(carService.getCarById(carId));
        response.setStatus(Status.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all cars", description = "Retrieve details of all cars")
    public ResponseEntity<BaseResponse<List<CarResponse>, String>> getAllCars() {
        BaseResponse<List<CarResponse>, String> response = new BaseResponse<>();
        response.setData(carService.getAllCarDetails());
        response.setStatus(Status.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getPaginationAndSorting")
    @Operation(summary = "Get cars with pagination and sorting", description = "Retrieve cars with pagination and sorting")
    public ResponseEntity<BaseResponse<Page<CarResponse>, String>> getCarsByPaginationAndSorting(
            @RequestParam int offset, @RequestParam int pageSize, @RequestParam String field,
            @RequestParam String order) {
        BaseResponse<Page<CarResponse>, String> response = new BaseResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setData(carService.getCarByPaginationAndSorting(offset, pageSize, field, order));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{carId}")
    @Operation(summary = "Update car details", description = "Update details of an existing car using its ID")
    public ResponseEntity<BaseResponse<CarResponse, String>> updateCar(
            @PathVariable String carId, @Valid @RequestBody CarRequest carRequest) {
        BaseResponse<CarResponse, String> response = new BaseResponse<>();
        response.setData(carService.updateCarById(carId, carRequest));
        response.setStatus(Status.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/soft-delete/{carId}")
    @Operation(summary = "Soft delete a car", description = "Soft delete a car by its ID (mark as deleted without removing from DB)")
    public ResponseEntity<BaseResponse<String, String>> softDeleteCar(@PathVariable String carId) {
        BaseResponse<String, String> response = new BaseResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setData(carService.SoftDeleteCarById(carId)); // Service method to soft delete car
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/hard-delete/{carId}")
    @Operation(summary = "Hard delete a car", description = "Hard delete a car by its ID (remove permanently from DB)")
    public ResponseEntity<BaseResponse<String, String>> hardDeleteCar(@PathVariable String carId) {
        BaseResponse<String, String> response = new BaseResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setData(carService.HardDeleteCarById(carId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //    Filter controller
    @GetMapping("/filter")
    @Operation(
            summary = "Filter cars by name, model, year, and sort them",
            description = "This endpoint allows users to filter cars based on name, model, year, and sort them by a specified field in ascending or descending order." +
                    " If no cars match the provided filter criteria, a 404 Not Found response will be returned."
    )
    public ResponseEntity<List<Car>> getFilteredCars(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam String sortBy,  // Removed defaultValue
            @RequestParam boolean ascending) {  // Removed defaultValue
        List<Car> cars = carService.getFilteredCars(name, model, year, sortBy, ascending);
        if (cars.isEmpty()) {
            throw new CarNotFoundException("No cars found matching the provided filters.");
        }
        return ResponseEntity.ok(cars);
    }


    //    Delete car by name
    @DeleteMapping("/deleteByName/{name}")
    @Operation(
            summary = "Delete car by name",
            description = "This endpoint allows users to delete a car based on its name." +
                    " If a car with the provided name is found, it will be deleted. If no car with the specified name exists, " +
                    "a 404 Not Found response will be returned."
    )
    public ResponseEntity<Map<String, Object>> deleteCarByName(@PathVariable String name) {
        try {
            Map<String, Object> response = carService.deleteCarByName(name);
            if ("FAILURE".equals(response.get("status"))) {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle unexpected errors
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "An unexpected error occurred.");
            errorResponse.put("status", "FAILURE");
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Global Searching
    @Operation(
            summary = "Global Search for Cars",
            description = "Search cars by name, model, year, color, or fuel type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> globalSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String fuelType) {
        return carService.globalSearch(name, model, year, color, fuelType);
    }


}
