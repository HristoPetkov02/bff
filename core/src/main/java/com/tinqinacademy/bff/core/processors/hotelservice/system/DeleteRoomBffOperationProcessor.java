package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom.DeleteRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeleteRoomBffOperationProcessor extends BaseOperationProcessor<DeleteRoomBffInput, DeleteRoomBffOutput> implements DeleteRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public DeleteRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, DeleteRoomBffOutput> process(DeleteRoomBffInput input) {
        return Try.of(() -> deleteRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private DeleteRoomBffOutput deleteRoom(DeleteRoomBffInput input) {
        logStart(input);
        validateInput(input);

        hotelRestExport.deleteRoom(input.getId());
        DeleteRoomBffOutput output = DeleteRoomBffOutput.builder().build();
        logEnd(output);
        return output;
    }
}
