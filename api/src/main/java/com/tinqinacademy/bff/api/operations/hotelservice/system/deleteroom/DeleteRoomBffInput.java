package com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteRoomBffInput implements OperationBffInput {
    @UUID(message = "Invalid UUID format")
    private String id;
}
