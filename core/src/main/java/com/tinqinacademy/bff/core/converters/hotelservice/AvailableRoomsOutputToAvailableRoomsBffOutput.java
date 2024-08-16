package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;

import com.tinqinacademy.hotel.api.operations.hotel.availablerooms.AvailableRoomsOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AvailableRoomsOutputToAvailableRoomsBffOutput extends BaseConverter<AvailableRoomsOutput, AvailableRoomsBffOutput> {
    @Override
    protected AvailableRoomsBffOutput convertObject(AvailableRoomsOutput input) {
        AvailableRoomsBffOutput output = AvailableRoomsBffOutput.builder()
                .ids(input.getIds())
                .build();
        return output;
    }
}
