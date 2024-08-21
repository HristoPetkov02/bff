package com.tinqinacademy.bff.core.processors.commentsservice.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.leavecomment.LeaveCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecomment.LeaveCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class LeaveCommentBffOperationProcessor extends BaseOperationProcessor<LeaveCommentBffInput, LeaveCommentBffOutput> implements LeaveCommentBffOperation {
    private final CommentsRestExport commentsRestExport;
    private final HotelRestExport hotelRestExport;

    public LeaveCommentBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, CommentsRestExport commentsRestExport, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.commentsRestExport = commentsRestExport;
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, LeaveCommentBffOutput> process(LeaveCommentBffInput input) {
        return Try.of(() -> leaveComment(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private LeaveCommentBffOutput leaveComment(LeaveCommentBffInput input) {
        logStart(input);
        validateInput(input);

        hotelRestExport.getRoom(input.getRoomId());

        LeaveCommentInput inputForComments = conversionService.convert(input,LeaveCommentInput.class);
        LeaveCommentOutput outputFromComments = commentsRestExport.leaveComment(input.getRoomId(),inputForComments);

        LeaveCommentBffOutput output = conversionService.convert(outputFromComments,LeaveCommentBffOutput.class);
        logEnd(output);
        return output;
    }
}
