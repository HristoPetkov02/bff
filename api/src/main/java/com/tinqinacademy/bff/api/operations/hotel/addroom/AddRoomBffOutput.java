package com.tinqinacademy.bff.api.operations.hotel.addroom;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoomBffOutput implements OperationOutput {
    private String id;
}
