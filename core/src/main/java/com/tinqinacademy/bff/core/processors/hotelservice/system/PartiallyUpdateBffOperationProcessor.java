package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateInput;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartiallyUpdateBffOperationProcessor extends BaseOperationProcessor<PartiallyUpdateBffInput, PartiallyUpdateBffOutput> implements PartiallyUpdateBffOperation {
    private final HotelRestExport hotelRestExport;

    public PartiallyUpdateBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, PartiallyUpdateBffOutput> process(PartiallyUpdateBffInput input) {
        return Try.of(() -> partiallyUpdate(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private PartiallyUpdateBffOutput partiallyUpdate(PartiallyUpdateBffInput input) {
        logStart(input);
        validateInput(input);

        PartiallyUpdateInput inputForHotel = conversionService.convert(input, PartiallyUpdateInput.class);
        PartiallyUpdateOutput outputFromHotel = hotelRestExport.partiallyUpdate(input.getRoomId(),inputForHotel);
        PartiallyUpdateBffOutput output = conversionService.convert(outputFromHotel, PartiallyUpdateBffOutput.class);

        logEnd(output);
        return output;
    }
}
