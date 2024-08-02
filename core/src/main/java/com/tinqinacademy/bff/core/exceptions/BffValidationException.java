package com.tinqinacademy.bff.core.exceptions;

import com.tinqinacademy.bff.api.model.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BffValidationException extends RuntimeException{
    private final List<Error> errors;
    private final HttpStatus status;

    public BffValidationException(String message, List<Error> errors, HttpStatus status){
        super(message);
        this.errors = errors;
        this.status = status;
    }
}
