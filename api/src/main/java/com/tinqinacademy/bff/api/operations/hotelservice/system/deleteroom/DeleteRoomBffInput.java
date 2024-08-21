package com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteRoomBffInput implements OperationBffInput {
    private String id;
}
