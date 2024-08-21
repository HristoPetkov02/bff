package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;

import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddRoomOutputToAddRoomBffOutput extends BaseConverter<AddRoomOutput, AddRoomBffOutput> {
    @Override
    protected AddRoomBffOutput convertObject(AddRoomOutput output) {
        AddRoomBffOutput addRoomBffOutput = AddRoomBffOutput.builder()
                .id(output.getId())
                .build();
        return addRoomBffOutput;
    }
}
