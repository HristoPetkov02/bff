package com.tinqinacademy.bff.core.converters.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.comments.api.operations.hotel.updateowncomment.UpdateOwnCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateOwnCommentOutputToUpdateOwnCommentBffOutput extends BaseConverter<UpdateOwnCommentOutput, UpdateOwnCommentBffOutput>{
    @Override
    protected UpdateOwnCommentBffOutput convertObject(UpdateOwnCommentOutput input) {
        UpdateOwnCommentBffOutput output = UpdateOwnCommentBffOutput.builder()
                .id(input.getId())
                .build();
        return output;
    }
}
