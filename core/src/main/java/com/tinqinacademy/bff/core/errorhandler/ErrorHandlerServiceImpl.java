package com.tinqinacademy.bff.core.errorhandler;


import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.Error;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.exceptions.BffApiException;
import com.tinqinacademy.bff.api.exceptions.BffValidationException;
import io.vavr.API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Component
public class ErrorHandlerServiceImpl implements ErrorHandlerService {
    @Override
    public ErrorWrapper handle(Throwable throwable) {
        return Match(throwable).of(
                API.Case(API.$(instanceOf(BffApiException.class)), this::handleBffApiException),
                API.Case(API.$(instanceOf(BffValidationException.class)), this::handleBffValidationException),
                Case($(), this::handleDefaultException)
        );
    }

    private ErrorWrapper handleBffApiException(BffApiException ex) {
        return ErrorWrapper.builder()
                .errorCode(ex.getHttpStatus())
                .errors(
                        List.of(
                                Error.builder()
                                        .message(ex.getMessage())
                                        .build()))
                .build();
    }

    private ErrorWrapper handleBffValidationException(BffValidationException ex) {
        return ErrorWrapper.builder()
                .errorCode(ex.getStatus())
                .errors(ex.getErrors())
                .build();
    }

    private ErrorWrapper handleDefaultException(Throwable ex) {
        return ErrorWrapper.builder()
                .errorCode(HttpStatus.BAD_REQUEST)
                .errors(
                        List.of(
                                Error.builder()
                                        .message(ex.getMessage())
                                        .build()))
                .build();
    }
}
