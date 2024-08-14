package com.tinqinacademy.bff.core.errorhandler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.Error;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.exceptions.BffApiException;
import com.tinqinacademy.bff.api.exceptions.BffValidationException;
import feign.FeignException;
import io.vavr.API;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorHandlerServiceImpl implements ErrorHandlerService {
    private final ObjectMapper objectMapper;
    @Override
    public ErrorWrapper handle(Throwable throwable) {
        return Match(throwable).of(
                API.Case(API.$(instanceOf(BffApiException.class)), this::handleBffApiException),
                API.Case(API.$(instanceOf(BffValidationException.class)), this::handleBffValidationException),
                API.Case(API.$(instanceOf(FeignException.class)), this::handleFeignException),
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


    private ErrorWrapper handleFeignException(FeignException ex) {
        try {
            String responseBody = ex.contentUTF8();

            List<Error> errors = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(List.class, Error.class));

            return ErrorWrapper.builder()
                    .errors(errors)
                    .errorCode(HttpStatus.valueOf(ex.status()))
                    .build();
        } catch (IOException e) {
            return ErrorWrapper.builder()
                    .errors(Collections.singletonList(Error.builder().message(ex.getMessage()).build()))
                    .errorCode(HttpStatus.valueOf(ex.status()))
                    .build();
        }
    }


    /*private ErrorWrapper handleFeignException(FeignException ex) {
        String defaultErrorMessage = "Unknown error occurred";
        List<Error> errorList = new ArrayList<>();

        try {
            String responseBody = ex.contentUTF8();

            List<Map<String, String>> responseList = objectMapper.readValue(responseBody, List.class);

            if (!responseList.isEmpty()) {
                Map<String, String> firstError = responseList.get(0);

                // This if is for the case, if there is no field in the error response,
                // otherwise it will return IllegalArgumentException in the console
                if (firstError.containsKey("field") && firstError.containsKey("message")) {
                    for (Map<String, String> errorMap : responseList) {
                        String field = errorMap.get("field");
                        String message = errorMap.get("message");
                        errorList.add(Error.builder()
                                .field(field)
                                .message(message)
                                .build());
                    }
                } else if (firstError.containsKey("message")) {
                    String errMsg = firstError.get("message");
                    errorList.add(Error.builder().message(errMsg).build());
                }
            } else {
                errorList.add(Error.builder().message(defaultErrorMessage).build());
            }

        } catch (IOException e) {
            errorList.add(Error.builder().message(ex.getMessage()).build());
        }

        return ErrorWrapper.builder()
                .errors(errorList)
                .errorCode(HttpStatus.valueOf(ex.status()))
                .build();
    }*/
}
