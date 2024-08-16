package com.tinqinacademy.bff.api.operations.hotel.availablerooms;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableRoomsBffOutput implements OperationBffOutput {
    private List<String> ids;
}
