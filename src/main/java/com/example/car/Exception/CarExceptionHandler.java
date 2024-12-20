package com.example.car.Exception;

import com.example.car.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarExceptionHandler {
    private String errorMsg;
    private HttpStatus httpStatus;
    Status status = Status.SUCCESS;
}
