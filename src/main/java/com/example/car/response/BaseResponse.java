package com.example.car.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T, ID> extends GenericResponse {
    T data;
    ID id;
}
