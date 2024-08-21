package com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoomBffOutput implements OperationBffOutput {
    private String id;
}
