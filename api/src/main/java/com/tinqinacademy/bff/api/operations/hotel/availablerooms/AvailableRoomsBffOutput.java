package com.tinqinacademy.bff.api.operations.hotel.availablerooms;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableRoomsBffOutput implements OperationOutput {
    private List<String> ids;
}
