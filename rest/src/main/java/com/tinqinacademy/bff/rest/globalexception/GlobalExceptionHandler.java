package com.tinqinacademy.bff.rest.globalexception;


import com.tinqinacademy.hotel.api.interfaces.ExceptionService;
import com.tinqinacademy.hotel.api.model.ErrorWrapper;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {



   /* @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException e) {
        return ResponseEntity.status(e.status())
              .body(e.getMessage());
    }*/

}

