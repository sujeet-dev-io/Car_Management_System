package com.example.car.Repository;

import com.example.car.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car> {

    @Query("SELECT c FROM Car c WHERE "
            + "(COALESCE(:name, '') = '' OR c.name LIKE %:name%) AND "
            + "(COALESCE(:model, '') = '' OR c.model LIKE %:model%) AND "
            + "(COALESCE(:year, '') = '' OR c.year = :year) AND "
            + "(COALESCE(:color, '') = '' OR c.color LIKE %:color%) AND "
            + "(COALESCE(:fuelType, '') = '' OR c.fuelType LIKE %:fuelType%)")
    List<Car> findCarsByMultipleCriteria(@Param("name") String name,
                                         @Param("model") String model,
                                         @Param("year") Integer year,
                                         @Param("color") String color,
                                         @Param("fuelType") String fuelType);

    Optional<Car> findByCarIdAndDeletedFalse(String carId);

    @Query("SELECT c FROM Car c WHERE c.name = :name")
    Car findByName(@Param("name") String name);

}
