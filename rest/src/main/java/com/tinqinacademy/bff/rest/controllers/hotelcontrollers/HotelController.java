package com.tinqinacademy.bff.rest.controllers.hotelcontrollers;

import com.tinqinacademy.bff.api.operations.hotel.availablerooms.AvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hotel.availablerooms.AvailableRoomsBffOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotel.getroom.GetRoomBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final AvailableRoomsBffOperation availableRoomsBffOperation;
    private final GetRoomBffOperation getRoomBffOperation;

    @Operation(summary = "Check available rooms",
            description = " This endpoint is for checking available rooms by criteria",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully have been displayed the ids for the available rooms "),
            @ApiResponse(responseCode = "400", description = "There was an error while trying to get the available rooms"),
            @ApiResponse(responseCode = "404", description = "There are no available rooms with the required search criteria")
    })
    @GetMapping(BffRestApiRoutes.HOTEL_API_HOTEL_CHECK_AVAILABILITY)
    public ResponseEntity<?> checkAvailability(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(value = "bedCount", required = false) Integer bedCount,
            @RequestParam(value = "bedSize", required = false) String bedSize,
            @RequestParam(value = "bathroomType", required = false) String bathroomType) {
        AvailableRoomsBffInput input = AvailableRoomsBffInput
                .builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedCount(bedCount)
                .bedSize(bedSize)
                .bathroomType(bathroomType)
                .build();

        return handle(availableRoomsBffOperation.process(input));
    }


    @Operation(summary = "Search room by roomId",
            description = " This endpoint is for searching a room by roomId",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the room"),
            @ApiResponse(responseCode = "400", description = "Wrong roomId format used"),
            @ApiResponse(responseCode = "404", description = "A room with this roomId doesn't exist")
    })
    @GetMapping(BffRestApiRoutes.HOTEL_API_HOTEL_GET_ROOM)
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        GetRoomBffInput input = GetRoomBffInput
                .builder()
                .roomId(roomId)
                .build();
        return handle(getRoomBffOperation.process(input));
    }



}
