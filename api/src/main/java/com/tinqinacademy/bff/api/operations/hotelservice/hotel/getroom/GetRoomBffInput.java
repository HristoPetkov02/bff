package com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRoomBffInput implements OperationBffInput {
    @NotNull(message = "Room ID is required")
    @UUID(message = "Room ID must be a valid UUID")
    private String roomId;
}
