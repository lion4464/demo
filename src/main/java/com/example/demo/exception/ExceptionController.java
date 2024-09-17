package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(ApiException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleApiException(ApiException e, ServerWebExchange exchange) {
        log.error("API Exception: ", e);
        String requestURI = exchange.getRequest().getURI().toString();
        return Mono.just(
                ResponseEntity
                        .status(e.getHttpStatus())
                        .body(new ErrorResponse(
                                e.getHttpStatus().value(),
                                e.getClass().getSimpleName(),
                                requestURI,
                                e.getLocalizedMessage().substring(0,50),
                                e.getData()))
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDbDublicatKeyException(DataIntegrityViolationException e, ServerWebExchange exchange) {
        log.error("DataIntegrityViolationException ", e);
        String requestURI = exchange.getRequest().getURI().toString();
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                e.getClass().getSimpleName(),
                                requestURI,
                                e.getLocalizedMessage(),
                                null))
        );
    }


}
