package com.tinqinacademy.bff.rest.controllers.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.getcomments.GetRoomCommentsBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.leavecomment.LeaveCommentBffOutput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.hotel.updateowncomment.UpdateOwnCommentBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import com.tinqinacademy.bff.rest.context.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HotelCommentsController extends BaseController {
    private final GetRoomCommentsBffOperation getRoomCommentsBffOperation;
    private final LeaveCommentBffOperation leaveCommentBffOperation;
    private final UpdateOwnCommentBffOperation updateOwnCommentBffOperation;
    private final UserContext userContext;

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

    @Operation(summary = "Leave a comment",
            description = " leaves a comment for a room",
            tags = {"Comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a comment"),
            @ApiResponse(responseCode = "400", description = "Wrong roomId format used"),
            @ApiResponse(responseCode = "404", description = "A room with this roomId doesn't exist")
    })
    @PostMapping(BffRestApiRoutes.COMMENTS_API_HOTEL_LEAVE_COMMENT)
    public ResponseEntity<?> leaveComment(
            @PathVariable String roomId,
            @RequestBody LeaveCommentBffInput input){
        LeaveCommentBffInput updatedInput = input.toBuilder()
                .roomId(roomId)
                .userId(userContext.getUserId())
                .build();
        return handleWithCode(leaveCommentBffOperation.process(updatedInput), HttpStatus.CREATED);
    }

    @Operation(summary = "Update own comment",
            description = " updates your own comment",
            tags = {"Comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the comment"),
            @ApiResponse(responseCode = "400", description = "Wrong commentId format used"),
            @ApiResponse(responseCode = "404", description = "A comment with this commentId doesn't exist")
    })
    @PatchMapping(BffRestApiRoutes.COMMENTS_API_HOTEL_UPDATE_OWN_COMMENT)
    public ResponseEntity<?> updateOwnComment(
            @PathVariable String commentId,
            @RequestBody UpdateOwnCommentBffInput input){
        UpdateOwnCommentBffInput updatedInput = input.toBuilder()
                .commentId(commentId)
                .userId(userContext.getUserId())
                .build();
        return handle(updateOwnCommentBffOperation.process(updatedInput));
    }
}
