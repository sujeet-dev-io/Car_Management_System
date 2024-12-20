package com.example.car;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class CarManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarManagementSystemApplication.class, args);
		System.out.println("Hello InternShip");
	}

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
