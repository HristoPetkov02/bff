package com.tinqinacademy.bff.core.processors.commentsservice.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.updateowncomment.UpdateOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.updateowncomment.UpdateOwnCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class UpdateOwnCommentBffOperationProcessor extends BaseOperationProcessor<UpdateOwnCommentBffInput, UpdateOwnCommentBffOutput> implements UpdateOwnCommentBffOperation {
    private final CommentsRestExport commentsRestExport;

    public UpdateOwnCommentBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<ErrorWrapper, UpdateOwnCommentBffOutput> process(UpdateOwnCommentBffInput input) {
        return Try.of(() -> updateOwnComment(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private UpdateOwnCommentBffOutput updateOwnComment(UpdateOwnCommentBffInput input) {
        logStart(input);
        validateInput(input);

        UpdateOwnCommentInput inputForComments = conversionService.convert(input, UpdateOwnCommentInput.class);
        UpdateOwnCommentOutput outputFromComments = commentsRestExport.updateOwnComment(input.getCommentId(), inputForComments);

        UpdateOwnCommentBffOutput output = conversionService.convert(outputFromComments, UpdateOwnCommentBffOutput.class);
        logEnd(output);
        return output;
    }
}
