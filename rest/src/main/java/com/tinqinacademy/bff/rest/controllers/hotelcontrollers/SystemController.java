package com.tinqinacademy.bff.rest.controllers.hotelcontrollers;

import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.deleteroom.DeleteRoomBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffOperation;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
public class SystemController extends BaseController {
    private final AddRoomBffOperation addRoomBffOperation;
    private final RegisterVisitorsBffOperation registerVisitorsBffOperation;
    private final ReportBffOperation reportBffOperation;
    private final UpdateRoomBffOperation updateRoomBffOperation;
    private final PartiallyUpdateBffOperation partiallyUpdateBffOperation;
    private final DeleteRoomBffOperation deleteRoomBffOperation;


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


    @Operation(summary = "Report for visitors",
            description = " This endpoint is for displaying a report of visitors for a criteria",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful report displayed"),
            @ApiResponse(responseCode = "400", description = "Incorrect data format")
    })
    @GetMapping(BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT)
    public ResponseEntity<?> reportByCriteria(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String phoneNo,
            @RequestParam(required = false) String idCardNo,
            @RequestParam(required = false) LocalDate idCardValidity,
            @RequestParam(required = false) String idCardIssueAthority,
            @RequestParam(required = false) LocalDate idCardIssueDate,
            @RequestParam(required = false) String roomNo) {
        ReportBffInput input = ReportBffInput
                .builder()
                .idCardIssueAthority(idCardIssueAthority)
                .idCardIssueDate(idCardIssueDate)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .roomNo(roomNo)
                .startDate(startDate)
                .build();

        return handle(reportBffOperation.process(input));
    }


    @Operation(summary = "Update room",
            description = " This endpoint is for updating the info of a room",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The room was successfully updated"),
            @ApiResponse(responseCode = "400", description = "The roomId is in the wrong format"),
            @ApiResponse(responseCode = "404", description = "There is no room with this id")
    })
    @PutMapping(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@PathVariable String roomId, @RequestBody UpdateRoomBffInput input){
        UpdateRoomBffInput updatedInput = input.toBuilder()
                .roomId(roomId)
                .build();
        return handle(updateRoomBffOperation.process(updatedInput));
    }

    @Operation(summary = "Update partially room",
            description = " This endpoint is for updating partially the info of a room",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The room was successfully updated"),
            @ApiResponse(responseCode = "400", description = "The roomId is in the wrong format"),
            @ApiResponse(responseCode = "404", description = "There is no room with this id")
    })
    @PatchMapping(value = BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM, consumes = "application/json-patch+json")
    public ResponseEntity<?> partiallyUpdate(@PathVariable String roomId, @RequestBody PartiallyUpdateBffInput input){
        PartiallyUpdateBffInput updatedInput = input.toBuilder()
                .roomId(roomId)
                .build();
        return handle(partiallyUpdateBffOperation.process(updatedInput));
    }

    @Operation(summary = "Remove a room",
            description = " This endpoint is removing a room from the hotel",
            tags = {"Hotel"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The room was successfully removed"),
            @ApiResponse(responseCode = "400", description = "The roomId is in the wrong format"),
            @ApiResponse(responseCode = "404", description = "There is no room with this id")
    })
    @DeleteMapping(BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String id) {
        DeleteRoomBffInput input = DeleteRoomBffInput.builder()
                .id(id)
                .build();
        return handle(deleteRoomBffOperation.process(input));
    }
}
