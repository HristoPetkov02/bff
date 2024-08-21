package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentOutputToUpdateCommentBffOutput extends BaseConverter<UpdateCommentOutput, UpdateCommentBffOutput>{
    @Override
    protected UpdateCommentBffOutput convertObject(UpdateCommentOutput input) {
        UpdateCommentBffOutput output = UpdateCommentBffOutput.builder()
                .id(input.getId())
                .build();
        return output;
    }
}
