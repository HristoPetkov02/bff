package com.tinqinacademy.bff.api.interfaces;


import com.tinqinacademy.bff.api.model.ErrorWrapper;

public interface ErrorHandlerService {
    ErrorWrapper handle(Throwable throwable);
}
