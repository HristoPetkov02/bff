package com.tinqinacademy.bff.core.processors;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetRoomBffOperationProcessor extends BaseOperationProcessor<GetRoomBffInput, GetRoomBffOutput> implements GetRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public GetRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, GetRoomBffOutput> process(GetRoomBffInput input) {
        return Try.of(() -> getRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private GetRoomBffOutput getRoom(GetRoomBffInput input) {
        logStart(input);
        validateInput(input);

        GetRoomOutput outputFromHotel = hotelRestExport.getRoom(input.getRoomId());
        GetRoomBffOutput output = conversionService.convert(outputFromHotel, GetRoomBffOutput.class);

        logEnd(output);
        return output;
    }
}
