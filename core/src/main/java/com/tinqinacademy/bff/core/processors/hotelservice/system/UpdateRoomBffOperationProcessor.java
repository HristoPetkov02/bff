package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateRoomBffOperationProcessor extends BaseOperationProcessor<UpdateRoomBffInput, UpdateRoomBffOutput> implements UpdateRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public UpdateRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, UpdateRoomBffOutput> process(UpdateRoomBffInput input) {
        return Try.of(() -> updateRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private UpdateRoomBffOutput updateRoom(UpdateRoomBffInput input){
        logStart(input);
        validateInput(input);

        UpdateRoomInput inputForHotel = conversionService.convert(input, UpdateRoomInput.class);
        UpdateRoomOutput outputFromHotel = hotelRestExport.updateRoom(input.getRoomId(),inputForHotel);
        UpdateRoomBffOutput output = conversionService.convert(outputFromHotel, UpdateRoomBffOutput.class);

        logEnd(output);
        return output;
    }
}
