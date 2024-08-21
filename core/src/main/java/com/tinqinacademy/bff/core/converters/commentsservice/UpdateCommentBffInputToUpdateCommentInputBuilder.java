package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentBffInputToUpdateCommentInputBuilder extends BaseConverter<UpdateCommentBffInput, UpdateCommentInput.UpdateCommentInputBuilder>{
    @Override
    protected UpdateCommentInput.UpdateCommentInputBuilder convertObject(UpdateCommentBffInput input) {
        UpdateCommentInput.UpdateCommentInputBuilder output = UpdateCommentInput.builder()
                .userId(input.getUserId())
                .content(input.getContent());
        return output;
    }
}
