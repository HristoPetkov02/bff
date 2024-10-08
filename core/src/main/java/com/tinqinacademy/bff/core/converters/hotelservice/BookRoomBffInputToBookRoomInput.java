package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookRoomBffInputToBookRoomInput extends BaseConverter<BookRoomBffInput, BookRoomInput> {
    @Override
    protected BookRoomInput convertObject(BookRoomBffInput input) {
        BookRoomInput output = BookRoomInput.builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .userId(input.getUserId())
                .build();
        return output;
    }
}
