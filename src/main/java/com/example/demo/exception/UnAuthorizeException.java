package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UnAuthorizeException extends ApiException{
    public UnAuthorizeException(String message) {
        super(HttpStatus.UNAUTHORIZED,message);
    }
}
