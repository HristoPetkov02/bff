package com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoomCommentsBffInput implements OperationBffInput {
    @NotBlank(message = "Room id is required")
    @UUID(message = "Room id must be a valid UUID")
    private String roomId;
}
