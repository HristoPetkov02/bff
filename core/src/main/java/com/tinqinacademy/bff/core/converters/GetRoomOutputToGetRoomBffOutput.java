package com.tinqinacademy.bff.core.converters;


import com.tinqinacademy.bff.api.model.BathroomType;
import com.tinqinacademy.bff.api.model.BedSize;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
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
                .bathroomType(BathroomType.getByCode(input.getBathroomType().toString()))
                .datesOccupied(input.getDatesOccupied())
                .bedSizes(
                        input.getBedSizes().stream()
                                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                                .toList()
                )
                .build();
        return output;
    }
}
