package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.addroom.AddRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddRoomBffOperationProcessor extends BaseOperationProcessor<AddRoomBffInput, AddRoomBffOutput> implements AddRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public AddRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, AddRoomBffOutput> process(AddRoomBffInput input) {
        return Try.of(() -> addRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private AddRoomBffOutput addRoom(AddRoomBffInput input) {
        logStart(input);
        validateInput(input);

        AddRoomInput inputForHotel = conversionService.convert(input, AddRoomInput.class);
        AddRoomOutput outputFromHotel= hotelRestExport.addRoom(inputForHotel);
        AddRoomBffOutput output = conversionService.convert(outputFromHotel, AddRoomBffOutput.class);

        logEnd(output);
        return output;
    }
}
