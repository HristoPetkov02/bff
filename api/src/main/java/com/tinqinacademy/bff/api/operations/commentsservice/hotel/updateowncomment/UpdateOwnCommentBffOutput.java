package com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateOwnCommentBffOutput implements OperationBffOutput {
    private String id;
}
