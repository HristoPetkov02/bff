package com.tinqinacademy.bff.rest.controllers.hotelcontrollers;

import com.tinqinacademy.bff.api.operations.hotel.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotel.addroom.AddRoomBffOperation;
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
