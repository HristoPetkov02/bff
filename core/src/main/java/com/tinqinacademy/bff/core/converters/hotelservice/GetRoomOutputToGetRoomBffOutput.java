package com.tinqinacademy.bff.core.converters.hotelservice;


import com.tinqinacademy.bff.api.model.BffBathroomType;
import com.tinqinacademy.bff.api.model.BffBedSize;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom.GetRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;

import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetRoomOutputToGetRoomBffOutput extends BaseConverter<GetRoomOutput,GetRoomBffOutput> {
    @Override
    protected GetRoomBffOutput convertObject(GetRoomOutput input) {
        GetRoomBffOutput output = GetRoomBffOutput.builder()
                .id(input.getId())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bedCount(input.getBedCount())
                .bffBathroomType(BffBathroomType.getByCode(input.getBathroomType().toString()))
                .datesOccupied(input.getDatesOccupied())
                .bffBedSizes(
                        input.getBedSizes().stream()
                                .map(bedSize -> BffBedSize.getByCode(bedSize.toString()))
                                .toList()
                )
                .build();
        return output;
    }
}
