package com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DeleteCommentBffInput implements OperationBffInput {
    @UUID(message = "Comment ID must be a valid UUID")
    private String commentId;
}
