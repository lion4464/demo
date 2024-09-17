package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse implements Serializable {
    private int status;
    private String error;
    private String path;
    private String message;
    private Object data;
    private final Date timestamp = new Date();


}