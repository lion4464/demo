package com.example.demo.exception;

import com.example.demo.generic.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(
                new ResponseDto<>(
                        ResponseDto.States.ERROR,
                        HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null
                ),
                HttpStatus.NOT_FOUND
        );
    }

}