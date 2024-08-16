package com.tinqinacademy.bff.rest.controllers.hotelcontrollers;

import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SystemController extends BaseController {
    private final AddRoomBffOperation addRoomBffOperation;
    private final RegisterVisitorsBffOperation registerVisitorsBffOperation;


    @Operation(summary = "Register visitors",
            description = " This endpoint is registering a list of visitors as a room renters",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The visitors have been registered"),
            @ApiResponse(responseCode = "400", description = "Incorrect data format"),
            @ApiResponse(responseCode = "404", description = "The booking was not found")
    })
    @PostMapping(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
    public ResponseEntity<?> registerVisitors(@RequestBody RegisterVisitorsBffInput input) {
        return handleWithCode(registerVisitorsBffOperation.process(input), HttpStatus.CREATED);
    }



    @Operation(summary = "Adds a room",
            description = " This endpoint is for adding a room to the hotel",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The room was added/created"),
            @ApiResponse(responseCode = "400", description = "The room already exists")
    })
    @PostMapping(BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM)
    public ResponseEntity<?> addRoom(@RequestBody AddRoomBffInput input) {

        return handleWithCode(addRoomBffOperation.process(input), HttpStatus.CREATED);
    }
}
