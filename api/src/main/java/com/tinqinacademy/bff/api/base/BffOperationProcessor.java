package com.tinqinacademy.bff.api.base;


import com.tinqinacademy.bff.api.model.ErrorWrapper;
import io.vavr.control.Either;

public interface BffOperationProcessor<I extends OperationBffInput, O extends OperationBffOutput>{
    Either<ErrorWrapper,O> process(I input);
}
