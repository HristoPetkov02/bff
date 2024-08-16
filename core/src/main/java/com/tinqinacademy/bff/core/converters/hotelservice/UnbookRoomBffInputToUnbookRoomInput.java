package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnbookRoomBffInputToUnbookRoomInput extends BaseConverter<UnbookRoomBffInput, UnbookRoomInput>{
    @Override
    protected UnbookRoomInput convertObject(UnbookRoomBffInput input) {
        UnbookRoomInput output = UnbookRoomInput.builder()
                .userId(input.getUserId())
                .build();
        return output;
    }
}
