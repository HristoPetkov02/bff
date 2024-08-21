package com.tinqinacademy.bff.core.processors.commentsservice.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetRoomCommentsBffOperationProcessor extends BaseOperationProcessor<GetRoomCommentsBffInput, GetRoomCommentsBffOutput> implements GetRoomCommentsBffOperation {
    private final CommentsRestExport commentsRestExport;
    private final HotelRestExport hotelRestExport;

    public GetRoomCommentsBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, CommentsRestExport commentsRestExport, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.commentsRestExport = commentsRestExport;
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, GetRoomCommentsBffOutput> process(GetRoomCommentsBffInput input) {
        return Try.of(() -> getRoomComments(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private GetRoomCommentsBffOutput getRoomComments(GetRoomCommentsBffInput input) {
        logStart(input);
        validateInput(input);

        // Get a room by id from hotel service.
        // If there isn't a room, it won't continue because it will throw an exception.
        hotelRestExport.getRoom(input.getRoomId());

        GetRoomCommentsOutput outputFromCommentsService = commentsRestExport.getRoomComments(input.getRoomId());

        GetRoomCommentsBffOutput output =conversionService.convert(outputFromCommentsService, GetRoomCommentsBffOutput.class);
        logEnd(output);
        return output;
    }
}
