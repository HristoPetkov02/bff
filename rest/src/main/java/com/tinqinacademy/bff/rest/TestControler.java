package com.tinqinacademy.bff.rest;

import com.tinqinacademy.bff.domain.feignclients.HotelClient;
import com.tinqinacademy.hotel.api.operations.availablerooms.AvailableRoomsOutput;
import com.tinqinacademy.hotel.api.operations.partiallyupdate.PartiallyUpdateInput;
import com.tinqinacademy.hotel.api.operations.partiallyupdate.PartiallyUpdateOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.restroutes.RestApiRoutes;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestControler {
    private final HotelRestExport hotelRestExport;
    private final HotelClient hotelClient;


    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(hotelClient.getRoom(roomId));/*
        try {
            return ResponseEntity.ok(hotelClient.getRoom(roomId));
        } catch (FeignException e) {
            log.error("ResponseBody: " + e.contentUTF8());
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
        }*/
    }

    @GetMapping("/check-availability")
    public ResponseEntity<AvailableRoomsOutput> checkAvailability(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(value = "bedCount", required = false) Integer bedCount,
            @RequestParam(value = "bedSize", required = false) String bedSize,
            @RequestParam(value = "bathroomType", required = false) String bathroomType) {
        AvailableRoomsOutput availableRooms = hotelRestExport.checkAvailability(startDate, endDate, bedCount, bedSize, bathroomType);
        return ResponseEntity.ok(availableRooms);
    }

    @PutMapping(RestApiRoutes.API_SYSTEM_UPDATE_ROOM)
    public ResponseEntity<UpdateRoomOutput> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomInput input) {
        UpdateRoomOutput updateRoomOutput = hotelRestExport.updateRoom(roomId, input);
        return ResponseEntity.ok(updateRoomOutput);
    }

    @PatchMapping(value = RestApiRoutes.API_SYSTEM_UPDATE_PARTIALLY_ROOM, consumes = "application/json-patch+json")
    public ResponseEntity<PartiallyUpdateOutput> partiallyUpdate(@PathVariable String roomId, @RequestBody PartiallyUpdateInput input) {
        PartiallyUpdateOutput partiallyUpdateOutput = hotelRestExport.partiallyUpdate(roomId, input);
        return ResponseEntity.ok(partiallyUpdateOutput);
    }
}
