package com.tinqinacademy.bff.core.processors.commentsservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.hotel.getbyroomnumber.GetByRoomNumberOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UpdateCommentBffOperationProcessor extends BaseOperationProcessor<UpdateCommentBffInput, UpdateCommentBffOutput> implements UpdateCommentBffOperation {
    private final HotelRestExport hotelRestExport;
    private final CommentsRestExport commentsRestExport;

    public UpdateCommentBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport, CommentsRestExport commentsRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
        this.commentsRestExport = commentsRestExport;
    }

    @Override
    public Either<ErrorWrapper, UpdateCommentBffOutput> process(UpdateCommentBffInput input) {
        return Try.of(() -> updateComment(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }


    private UpdateCommentBffOutput updateComment(UpdateCommentBffInput input) {
        logStart(input);
        validateInput(input);

        GetByRoomNumberOutput outputFromHotel = hotelRestExport.getRoomByRoomNumber(input.getRoomNo());
        UpdateCommentInput inputForComments = Objects.requireNonNull(conversionService.convert(input, UpdateCommentInput.UpdateCommentInputBuilder.class))
                .roomId(outputFromHotel.getRoomId())
                .build();

        UpdateCommentOutput outputFromComments = commentsRestExport.updateComment(input.getCommentId(), inputForComments);
        UpdateCommentBffOutput output = conversionService.convert(outputFromComments, UpdateCommentBffOutput.class);
        logEnd(output);
        return output;
    }
}
