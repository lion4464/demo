package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends ApiException{
    public DataNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND,message);
    }
}
