package com.example.car.Exception;


import java.io.Serial;

public class NotFoundException  extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException  (String msg, Throwable cause) {
        super(msg,cause);
    }
}
