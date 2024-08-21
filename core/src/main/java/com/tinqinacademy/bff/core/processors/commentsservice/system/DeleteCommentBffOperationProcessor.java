package com.tinqinacademy.bff.core.processors.commentsservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment.DeleteCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment.DeleteCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment.DeleteCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class DeleteCommentBffOperationProcessor extends BaseOperationProcessor<DeleteCommentBffInput, DeleteCommentBffOutput> implements DeleteCommentBffOperation {
    private final CommentsRestExport commentsRestExport;

    public DeleteCommentBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, CommentsRestExport commentsRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<ErrorWrapper, DeleteCommentBffOutput> process(DeleteCommentBffInput input) {
        return Try.of(() -> deleteComment(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private DeleteCommentBffOutput deleteComment(DeleteCommentBffInput input) {
        logStart(input);

        validateInput(input);

        commentsRestExport.deleteComment(input.getCommentId());

        DeleteCommentBffOutput output = DeleteCommentBffOutput.builder().build();
        logEnd(output);
        return output;
    }
}
