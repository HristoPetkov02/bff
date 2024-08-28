package com.tinqinacademy.bff.rest.controllers.hotelsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.api.operations.validatejwt.ValidateJwtOutput;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.api.model.BffBathroomType;
import com.tinqinacademy.bff.api.model.BffBedSize;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.hotel.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtDecoder;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.model.BathroomType;
import com.tinqinacademy.hotel.api.model.BedSize;
import com.tinqinacademy.hotel.api.operations.hotel.availablerooms.AvailableRoomsOutput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelControllerTest {

    private final MockMvc mvc;

    private final ObjectMapper objectMapper;


    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private CommentsRestExport commentsRestExport;
    @MockBean
    private AuthRestExport authenticationRestExport;
    @MockBean
    private JwtDecoder jwtDecoder;


    @Autowired
    public HotelControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testCheckAvailabilityOk() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        int bedCount = 1;
        BffBedSize bffBedSize = BffBedSize.DOUBLE;
        BffBathroomType bffBathroomType = BffBathroomType.PRIVATE;
        when(hotelRestExport.checkAvailability(
                startDate, endDate, bedCount, bffBedSize.toString(), bffBathroomType.toString()))
                .thenReturn(new AvailableRoomsOutput());

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_HOTEL_CHECK_AVAILABILITY)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("bedCount", String.valueOf(bedCount))
                        .param("bedSize", bffBedSize.toString())
                        .param("bathroomType", bffBathroomType.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckAvailabilityBadRequest() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        int bedCount = 1;
        BffBedSize bffBedSize = BffBedSize.DOUBLE;
        BffBathroomType bffBathroomType = BffBathroomType.PRIVATE;
        when(hotelRestExport.checkAvailability(
                startDate, endDate, bedCount, bffBedSize.toString(), bffBathroomType.toString()))
                .thenReturn(AvailableRoomsOutput.builder().build());

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_HOTEL_CHECK_AVAILABILITY)
                        .param("startDate", startDate.toString())
                        .param("bathroomType", bffBathroomType.toString()))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testGetRoomOk() throws Exception {
        String roomId = UUID.randomUUID().toString();
        when(hotelRestExport.getRoom(roomId)).thenReturn(
                GetRoomOutput.builder()
                        .bathroomType(BathroomType.PRIVATE)
                        .bedCount(1)
                        .bedSizes(List.of(BedSize.DOUBLE))
                        .datesOccupied(List.of(LocalDate.of(2024, 1, 1)))
                        .floor(1)
                        .price(BigDecimal.valueOf(111))
                        .build());

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_HOTEL_GET_ROOM, roomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRoomBadRequest() throws Exception {
        mvc.perform(get(BffRestApiRoutes.HOTEL_API_HOTEL_GET_ROOM, "not-uuid"))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testBookRoomCreated() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();
        BookRoomInput bookRoomInput = BookRoomInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .userId(userId)
                .build();
        BookRoomBffInput input = BookRoomBffInput.builder()
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2025, 1, 1))
                .build();
        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        when(hotelRestExport.bookRoom(roomId, bookRoomInput)).thenReturn(BookRoomOutput.builder().build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM, roomId)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testBookRoomBadRequest() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwt = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();
        BookRoomBffInput input = BookRoomBffInput.builder()
                .build();
        when(jwtDecoder.decodeJwt(any(jwt.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwt)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM, roomId)
                        .header("Authorization", jwt)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookRoomUnauthorized() throws Exception {
        String roomId = UUID.randomUUID().toString();
        String jwt = "Bearer mock-jwt-token";

        when(authenticationRestExport.validateJwt(jwt)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_HOTEL_BOOK_ROOM, roomId)
                        .header("Authorization", jwt))
                .andExpect(status().isUnauthorized());
    }



    @Test
    public void testUnbookRoomOk() throws Exception {
        String bookingId = UUID.randomUUID().toString();
        String jwt = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();

        UnbookRoomBffInput input = UnbookRoomBffInput.builder()
                .build();

        UnbookRoomInput bookRoomInput = UnbookRoomInput.builder()
                .bookingId(bookingId)
                .userId(userId)
                .build();

        when(jwtDecoder.decodeJwt(any(jwt.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwt)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());
        when(hotelRestExport.unbookRoom(bookingId, bookRoomInput))
                .thenReturn(UnbookRoomOutput.builder().build());

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM, bookingId)
                        .header("Authorization", jwt)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnbookRoomBadRequest() throws Exception {
        String bookingId = "not-uuid";
        String jwt = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();

        UnbookRoomBffInput input = UnbookRoomBffInput.builder()
                .build();

        when(jwtDecoder.decodeJwt(any(jwt.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwt)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM, bookingId)
                        .header("Authorization", jwt)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUnbookRoomUnauthorized() throws Exception {
        String bookingId = UUID.randomUUID().toString();
        String jwt = "Bearer mock-jwt-token";

        when(authenticationRestExport.validateJwt(jwt)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_HOTEL_UNBOOK_ROOM, bookingId)
                        .header("Authorization", jwt))
                .andExpect(status().isUnauthorized());
    }
}
