package com.tinqinacademy.bff.rest.controllers.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelCommentsController extends BaseController {
    private final GetRoomCommentsBffOperation getRoomCommentsBffOperation;

    @Operation(summary = "Get all comments",
            description = " gets all comments of a room",
            tags = {"Comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully displayed all comments"),
            @ApiResponse(responseCode = "400", description = "Wrong roomId format used"),
            @ApiResponse(responseCode = "404", description = "A room with this roomId doesn't exist")
    })
    @GetMapping(BffRestApiRoutes.COMMENTS_API_HOTEL_GET_ROOM_COMMENT)
    public ResponseEntity<?> getRoomComments(@PathVariable String roomId) {
        GetRoomCommentsBffInput input = GetRoomCommentsBffInput.builder()
                .roomId(roomId)
                .build();
        return handle(getRoomCommentsBffOperation.process(input));
    }
}
