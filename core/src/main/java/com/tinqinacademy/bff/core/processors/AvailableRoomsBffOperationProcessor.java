package com.tinqinacademy.bff.core.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.availablerooms.AvailableRoomsOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AvailableRoomsBffOperationProcessor extends BaseOperationProcessor<AvailableRoomsBffInput, AvailableRoomsBffOutput> implements AvailableRoomsBffOperation {
    private final HotelRestExport hotelRestExport;

    public AvailableRoomsBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, AvailableRoomsBffOutput> process(AvailableRoomsBffInput input) {
        return Try.of(() -> checkAvailableRooms(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }


    private AvailableRoomsBffOutput checkAvailableRooms(AvailableRoomsBffInput input) {
        logStart(input);
        validateInput(input);


        AvailableRoomsOutput outputFromHotel = hotelRestExport.checkAvailability(
                input.getStartDate(),
                input.getEndDate(),
                input.getBedCount(),
                input.getBedSize(),
                input.getBathroomType()
        );

        AvailableRoomsBffOutput output = conversionService.convert(outputFromHotel, AvailableRoomsBffOutput.class);
        logEnd(output);
        return output;
    }
}
