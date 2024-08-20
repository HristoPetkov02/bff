package com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LeaveCommentBffOutput implements OperationBffOutput {
    private String id;
}
