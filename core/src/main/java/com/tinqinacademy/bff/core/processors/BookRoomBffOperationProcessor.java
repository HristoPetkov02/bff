package com.tinqinacademy.bff.core.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BookRoomBffOperationProcessor extends BaseOperationProcessor<BookRoomBffInput, BookRoomBffOutput> implements BookRoomBffOperation {
    private final HotelRestExport hotelRestExport;

    public BookRoomBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, BookRoomBffOutput> process(BookRoomBffInput input) {
        return Try.of(() -> bookRoom(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private BookRoomBffOutput bookRoom(BookRoomBffInput input) {
        logStart(input);
        validateInput(input);


        BookRoomInput inputForHotel = conversionService.convert(input, BookRoomInput.class);
        BookRoomOutput outputFromHotel = hotelRestExport.bookRoom(input.getRoomId(), inputForHotel);
        BookRoomBffOutput output = BookRoomBffOutput.builder().build();


        logEnd(output);
        return output;
    }
}
