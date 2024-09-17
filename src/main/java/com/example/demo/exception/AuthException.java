package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends ApiException{
    public AuthException(String message, String errorCode) {
        super(HttpStatus.UNAUTHORIZED,message);
    }
}
