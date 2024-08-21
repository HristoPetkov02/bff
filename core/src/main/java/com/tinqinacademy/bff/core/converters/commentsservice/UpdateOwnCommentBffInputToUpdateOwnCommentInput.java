package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.updateowncomment.UpdateOwnCommentInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateOwnCommentBffInputToUpdateOwnCommentInput extends BaseConverter<UpdateOwnCommentBffInput, UpdateOwnCommentInput>{
    @Override
    protected UpdateOwnCommentInput convertObject(UpdateOwnCommentBffInput input) {
        UpdateOwnCommentInput output = UpdateOwnCommentInput.builder()
                .content(input.getContent())
                .userId(input.getUserId())
                .build();
        return output;
    }
}
