package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PartiallyUpdateOutputToPartiallyUpdateBffOutput extends BaseConverter<PartiallyUpdateOutput, PartiallyUpdateBffOutput>{
    @Override
    protected PartiallyUpdateBffOutput convertObject(PartiallyUpdateOutput input) {
        PartiallyUpdateBffOutput output = PartiallyUpdateBffOutput.builder()
                .id(input.getId())
                .build();
        return output;
    }
}
