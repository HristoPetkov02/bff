package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateRoomBffInputToUpdateRoomInput extends BaseConverter<UpdateRoomBffInput, UpdateRoomInput> {
    @Override
    protected UpdateRoomInput convertObject(UpdateRoomBffInput input) {
        UpdateRoomInput output = UpdateRoomInput.builder()
                .bathroomType(input.getBathroomType())
                .roomNo(input.getRoomNo())
                .bedCount(input.getBedCount())
                .bedSizes(input.getBedSizes())
                .price(input.getPrice())
                .build();
        return output;
    }
}
