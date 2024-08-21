package com.tinqinacademy.bff.core.processors.hotelservice.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UnbookRoomBffOperationProcessor extends BaseOperationProcessor<UnbookRoomBffInput, UnbookRoomBffOutput> implements UnbookRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public UnbookRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }


    @Override
    public Either<ErrorWrapper, UnbookRoomBffOutput> process(UnbookRoomBffInput input) {
        return Try.of(() -> unbookRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private UnbookRoomBffOutput unbookRoom(UnbookRoomBffInput input) {
        logStart(input);
        validateInput(input);

        UnbookRoomInput inputForHotel = conversionService.convert(input, UnbookRoomInput.class);
        hotelRestExport.unbookRoom(input.getBookingId(), inputForHotel);

        UnbookRoomBffOutput output = UnbookRoomBffOutput.builder().build();

        logEnd(output);
        return output;
    }
}
