package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateRoomOutputToUpdateRoomBffOutput extends BaseConverter<UpdateRoomOutput, UpdateRoomBffOutput> {
    @Override
    protected UpdateRoomBffOutput convertObject(UpdateRoomOutput input) {
        UpdateRoomBffOutput output = UpdateRoomBffOutput.builder()
                .id(input.getId())
                .build();
        return output;
    }
}
