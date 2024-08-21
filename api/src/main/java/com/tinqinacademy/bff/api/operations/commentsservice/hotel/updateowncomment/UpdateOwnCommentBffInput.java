package com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment;

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
public class UpdateOwnCommentBffInput implements OperationBffInput {
    @JsonIgnore
    @UUID(message = "Comment id is not a valid UUID")
    private String commentId;

    @JsonIgnore
    @UUID(message = "User id is not a valid UUID")
    private String userId;

    @NotBlank(message = "Content is required")
    private String content;
}
