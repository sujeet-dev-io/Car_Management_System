package com.example.car.Exception;

import com.example.car.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            response.put(fieldName, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {NotFoundException.class })
    public ResponseEntity<Object> handleBookNotFoundEx(NotFoundException ex) {
        CarExceptionHandler handler = new CarExceptionHandler();
        handler.setStatus(Status.FAILURE);
        handler.setHttpStatus(HttpStatus.NOT_FOUND);
        handler.setErrorMsg("Id doesn't present" + ex.getMessage());
        log.error("Status code : Exception : [{}]  ", HttpStatus.NOT_FOUND, handler.getErrorMsg());
        return new ResponseEntity<>(handler, HttpStatus.NOT_FOUND);
    }


    // Handle BadRequestException
    @ExceptionHandler(value = { BadRequestException.class })
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        CarExceptionHandler handler = new CarExceptionHandler();
        handler.setStatus(Status.FAILURE);
        handler.setHttpStatus(HttpStatus.BAD_REQUEST);
        handler.setErrorMsg("Bad request: " + ex.getMessage());
        log.error("Status code: Exception: [{}] - {}", HttpStatus.BAD_REQUEST, handler.getErrorMsg());
        return new ResponseEntity<>(handler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFoundException(CarNotFoundException ex) {
        // Return the exception message with a 404 status code
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}

