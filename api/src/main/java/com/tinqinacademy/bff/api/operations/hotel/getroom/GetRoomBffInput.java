package com.tinqinacademy.bff.api.operations.hotel.getroom;

import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRoomBffInput implements OperationInput {
    @NotNull(message = "Room ID is required")
    private String roomId;
}
