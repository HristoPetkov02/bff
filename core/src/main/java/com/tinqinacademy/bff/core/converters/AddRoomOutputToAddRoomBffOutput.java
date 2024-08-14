package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.hotel.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddRoomOutputToAddRoomBffOutput extends BaseConverter<AddRoomOutput, AddRoomBffOutput> {
    @Override
    protected AddRoomBffOutput convertObject(AddRoomOutput output) {
        AddRoomBffOutput addRoomBffOutput = AddRoomBffOutput.builder()
                .id(output.getId())
                .build();
        return addRoomBffOutput;
    }
}
