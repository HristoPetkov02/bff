package com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate;


import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartiallyUpdateBffOutput implements OperationBffOutput {
    private String id;
}
