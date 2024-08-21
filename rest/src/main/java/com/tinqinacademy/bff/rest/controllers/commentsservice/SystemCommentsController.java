package com.tinqinacademy.bff.rest.controllers.commentsservice;

import com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment.DeleteCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.system.deletecomment.DeleteCommentBffOperation;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import com.tinqinacademy.bff.rest.context.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SystemCommentsController extends BaseController {
    private final UpdateCommentBffOperation updateCommentBffOperation;
    private final DeleteCommentBffOperation deleteCommentBffOperation;
    private final UserContext userContext;


    @Operation(summary = "Update comment",
            description = " updates a comment",
            tags = {"Comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the comment"),
            @ApiResponse(responseCode = "400", description = "Wrong commentId format used"),
            @ApiResponse(responseCode = "404", description = "A comment with this commentId doesn't exist")
    })
    @PutMapping(BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT)
    public ResponseEntity<?> updateComment(
            @PathVariable String commentId,
            @RequestBody UpdateCommentBffInput input){
        UpdateCommentBffInput updatedInput = input.toBuilder()
                .commentId(commentId)
                .userId(userContext.getUserId())
                .build();

        return handle(updateCommentBffOperation.process(updatedInput));
    }


    @Operation(summary = "Delete comment",
            description = " delete a comment",
            tags = {"Comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete the comment"),
            @ApiResponse(responseCode = "400", description = "Wrong commentId format used"),
            @ApiResponse(responseCode = "404", description = "A comment with this commentId doesn't exist")
    })
    @DeleteMapping(BffRestApiRoutes.COMMENTS_API_SYSTEM_DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@PathVariable String commentId){
        DeleteCommentBffInput input = DeleteCommentBffInput.builder()
                .commentId(commentId)
                .build();

        return handle(deleteCommentBffOperation.process(input));
    }
}
