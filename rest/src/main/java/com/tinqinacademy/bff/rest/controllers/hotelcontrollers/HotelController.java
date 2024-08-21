package com.tinqinacademy.bff.rest.controllers.hotelcontrollers;

import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.availablerooms.AvailableRoomsBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom.GetRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom.GetRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import com.tinqinacademy.bff.rest.context.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final UserContext userContext;
    private final AvailableRoomsBffOperation availableRoomsBffOperation;
    private final GetRoomBffOperation getRoomBffOperation;
    private final BookRoomBffOperation bookRoomBffOperation;
    private final UnbookRoomBffOperation unbookRoomBffOperation;

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



    @Operation(summary = "Book a room",
            description = " This endpoint is booking a room",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully has been booked the room "),
            @ApiResponse(responseCode = "400", description = "The room is unavailable"),
            @ApiResponse(responseCode = "404", description = "The room doesn't exist")
    })
    @PostMapping(BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM)
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,@RequestBody BookRoomBffInput input) {
        BookRoomBffInput updatedInput = input
                .toBuilder()
                .roomId(roomId)
                .userId(userContext.getUserId())
                .build();
        return handle(bookRoomBffOperation.process(updatedInput));
    }


    @Operation(summary = "Remove booked room",
            description = " This endpoint is removing the booked status of a room making it available using the bookingId",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The room was successfully unbooked"),
            @ApiResponse(responseCode = "400", description = "The bookingId is in the wrong format"),
            @ApiResponse(responseCode = "404", description = "There is no booked room with this bookingId")
    })
    @DeleteMapping(BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM)
    public ResponseEntity<?> unbookRoom(@PathVariable String bookingId) {
        UnbookRoomBffInput input = UnbookRoomBffInput
                .builder()
                .bookingId(bookingId)
                .userId(userContext.getUserId())
                .build();
        return handle(unbookRoomBffOperation.process(input));
    }
}
