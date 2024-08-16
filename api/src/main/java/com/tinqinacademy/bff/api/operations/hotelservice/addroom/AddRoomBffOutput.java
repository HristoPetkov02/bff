package com.tinqinacademy.bff.api.operations.hotelservice.addroom;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoomBffOutput implements OperationBffOutput {
    private String id;
}
