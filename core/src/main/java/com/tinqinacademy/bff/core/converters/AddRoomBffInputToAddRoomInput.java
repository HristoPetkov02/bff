package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.hotel.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class AddRoomBffInputToAddRoomInput extends BaseConverter<AddRoomBffInput, AddRoomInput>{
    @Override
    protected AddRoomInput convertObject(AddRoomBffInput input) {
        AddRoomInput addRoomInput = AddRoomInput.builder()
                .bathroomType(input.getBathroomType())
                .roomNo(input.getRoomNo())
                .bedCount(input.getBedCount())
                .bedSizes(input.getBedSizes())
                .floor(input.getFloor())
                .price(input.getPrice())
                .build();
        return addRoomInput;
    }
}
