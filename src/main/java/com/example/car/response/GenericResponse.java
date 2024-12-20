package com.example.car.response;

import com.example.car.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value= JsonInclude.Include.NON_EMPTY)

public class GenericResponse {
    String message;
    Status status = Status.SUCCESS;
    String error;
    String token;
}