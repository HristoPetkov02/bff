package com.tinqinacademy.bff.rest.controllers.commentsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtDecoder;
import com.tinqinacademy.comments.api.models.output.GetCommentOutput;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.hotel.getcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.hotel.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelCommentsControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private CommentsRestExport commentsRestExport;
    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private AuthRestExport authenticationRestExport;
    @MockBean
    private JwtDecoder jwtDecoder;

    @Autowired
    public HotelCommentsControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }


    @Test
    public void getRoomCommentsOk() throws Exception {
        when(hotelRestExport.getRoom(any(String.class)))
                .thenReturn(GetRoomOutput.builder().build());

        when(commentsRestExport.getRoomComments(any(String.class)))
                .thenReturn(GetRoomCommentsOutput.builder()
                        .commentOutputList(
                                List.of(
                                        GetCommentOutput.builder()
                                                .content("comment")
                                                .userId("userId")
                                                .publishDate(LocalDate.of(2021, 1, 1))
                                                .lastEditedDate(LocalDate.of(2021, 1, 1))
                                                .lastEditedBy("userId")
                                                .build()
                                )
                        )
                        .build());
        mvc.perform(get(BffRestApiRoutes.COMMENTS_API_HOTEL_GET_ROOM_COMMENT, UUID.randomUUID().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoomCommentsBadRequest() throws Exception {
        mvc.perform(get(BffRestApiRoutes.COMMENTS_API_HOTEL_GET_ROOM_COMMENT, "not-uuid"))
                .andExpect(status().isBadRequest());
    }
}
