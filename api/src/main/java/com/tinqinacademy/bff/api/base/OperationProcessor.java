package com.tinqinacademy.bff.api.base;


import com.tinqinacademy.bff.api.model.ErrorWrapper;
import io.vavr.control.Either;

public interface OperationProcessor <I extends OperationInput, O extends OperationOutput>{
    Either<ErrorWrapper,O> process(I input);
}
