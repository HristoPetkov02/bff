package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.model.output.GetCommentBffOutput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.models.output.GetCommentOutput;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetRoomCommentsOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetRoomCommentsOutputToGetRoomCommentsBffOutput extends BaseConverter<GetRoomCommentsOutput, GetRoomCommentsBffOutput> {
    @Override
    protected GetRoomCommentsBffOutput convertObject(GetRoomCommentsOutput input) {
        List<GetCommentBffOutput> commentBffOutputs = input.getCommentOutputList().stream().map(commentOutput -> GetCommentBffOutput.builder()
                .id(commentOutput.getId())
                .userId(commentOutput.getUserId())
                .content(commentOutput.getContent())
                .publishDate(commentOutput.getPublishDate())
                .lastEditedBy(commentOutput.getLastEditedBy())
                .lastEditedDate(commentOutput.getLastEditedDate())
                .build()).toList();
        GetRoomCommentsBffOutput output = GetRoomCommentsBffOutput.builder()
                .commentBffOutputs(commentBffOutputs)
                .build();
        return output;
    }
}
