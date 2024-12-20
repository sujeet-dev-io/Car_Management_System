package com.example.car.filter;

import com.example.car.Entity.Car;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CarFilter {

    public static Specification<Car> applyFilters(String name, String model, Integer year) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by name (case-insensitive)
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }
            // Filter by model (case-insensitive)
            if (model != null && !model.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("model")),
                        "%" + model.toLowerCase() + "%"
                ));
            }
            // Filter by year (exact match)
            if (year != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), year));
            }

            // Combine predicates with AND logic
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}