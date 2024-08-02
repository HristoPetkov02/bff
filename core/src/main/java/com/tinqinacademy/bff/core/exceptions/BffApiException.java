package com.tinqinacademy.bff.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BffApiException extends RuntimeException{

    private final HttpStatus httpStatus;

    public BffApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
}
