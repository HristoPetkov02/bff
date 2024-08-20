package com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import com.tinqinacademy.bff.api.model.output.GetCommentBffOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoomCommentsBffOutput implements OperationBffOutput {
    private List<GetCommentBffOutput> commentBffOutputs;
}
