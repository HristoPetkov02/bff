package com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UnbookRoomBffInput implements OperationBffInput {
    @NotNull(message = "Booking ID is required")
    @UUID(message = "Booking ID must be a valid UUID")
    private String bookingId;

    @NotBlank(message = "User ID is required")
    @UUID(message = "User ID must be a valid UUID")
    private String userId;
}
