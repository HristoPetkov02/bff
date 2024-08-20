package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.leavecomment.LeaveCommentInput;
import org.springframework.stereotype.Component;

@Component
public class LeaveCommentBffInputToLeaveCommentInput extends BaseConverter<LeaveCommentBffInput, LeaveCommentInput> {
    @Override
    protected LeaveCommentInput convertObject(LeaveCommentBffInput input) {
        LeaveCommentInput output = LeaveCommentInput.builder()
                .userId(input.getUserId())
                .content(input.getContent())
                .build();
        return output;
    }
}
