package com.tinqinacademy.bff.rest.base;



import com.tinqinacademy.bff.api.base.OperationBffOutput;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected<O extends OperationBffOutput> ResponseEntity<?> handleWithCode(Either<ErrorWrapper,O> result, HttpStatus status){
        return result.isLeft() ? error(result) : new ResponseEntity<>(result.get(), status);
    }

    protected<O extends OperationBffOutput> ResponseEntity<?> handle(Either<ErrorWrapper,O> result){
        return result.isLeft() ? error(result) : new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    private<O extends OperationBffOutput> ResponseEntity<?> error(Either<ErrorWrapper,O> result){
        return new ResponseEntity<>(result.getLeft().getErrors(), result.getLeft().getErrorCode());
    }
}
