package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ApiException extends Exception{
    private HttpStatus httpStatus;
    private Object data;


    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public ApiException(HttpStatus httpStatus) {
        this(httpStatus, null);
    }

    public ApiException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }
}
