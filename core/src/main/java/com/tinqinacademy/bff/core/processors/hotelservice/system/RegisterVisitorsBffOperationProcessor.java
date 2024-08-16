package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsInput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterVisitorsBffOperationProcessor extends BaseOperationProcessor<RegisterVisitorsBffInput, RegisterVisitorsBffOutput> implements RegisterVisitorsBffOperation {
    private final HotelRestExport hotelRestExport;

    public RegisterVisitorsBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, RegisterVisitorsBffOutput> process(RegisterVisitorsBffInput input) {
        return Try.of(() -> registerVisitors(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private RegisterVisitorsBffOutput registerVisitors(RegisterVisitorsBffInput input) {
        logStart(input);
        validateInput(input);


        RegisterVisitorsInput inputForHotel = conversionService.convert(input, RegisterVisitorsInput.class);
        hotelRestExport.registerVisitors(inputForHotel);
        RegisterVisitorsBffOutput output = RegisterVisitorsBffOutput.builder().build();
        logEnd(output);
        return output;
    }
}
