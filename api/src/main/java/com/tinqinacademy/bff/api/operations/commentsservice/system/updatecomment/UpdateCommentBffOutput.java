package com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateCommentBffOutput implements OperationBffOutput {
    private String id;
}
