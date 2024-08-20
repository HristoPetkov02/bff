package com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationBffInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class LeaveCommentBffInput implements OperationBffInput {
    @JsonIgnore
    @UUID(message = "Room id must be a valid UUID")
    private String roomId;

    @JsonIgnore
    @NotBlank(message = "User id is required")
    @UUID(message = "User id must be a valid UUID")
    private String userId;


    @NotBlank(message = "Comment content is required")
    private String content;
}
