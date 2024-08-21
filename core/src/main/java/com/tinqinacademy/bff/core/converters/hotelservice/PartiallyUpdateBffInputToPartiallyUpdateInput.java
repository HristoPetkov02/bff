package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PartiallyUpdateBffInputToPartiallyUpdateInput extends BaseConverter<PartiallyUpdateBffInput, PartiallyUpdateInput>{
    @Override
    protected PartiallyUpdateInput convertObject(PartiallyUpdateBffInput input) {
        PartiallyUpdateInput output = PartiallyUpdateInput.builder()
                .bathroomType(input.getBathroomType())
                .roomNo(input.getRoomNo())
                .bedCount(input.getBedCount())
                .bedSizes(input.getBedSizes())
                .price(input.getPrice())
                .build();
        return output;
    }
}
