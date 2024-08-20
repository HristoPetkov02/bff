package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.leavecomment.LeaveCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class LeaveCommentOutputToLeaveCommentBffOutput extends BaseConverter<LeaveCommentOutput, LeaveCommentBffOutput> {
    @Override
    protected LeaveCommentBffOutput convertObject(LeaveCommentOutput input) {
        LeaveCommentBffOutput output = LeaveCommentBffOutput.builder()
                .id(input.getId())
                .build();
        return output;
    }
}
