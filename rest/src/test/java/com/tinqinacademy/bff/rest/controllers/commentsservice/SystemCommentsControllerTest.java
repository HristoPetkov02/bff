package com.tinqinacademy.bff.rest.controllers.commentsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.api.operations.validatejwt.ValidateJwtOutput;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffInput;
import com.tinqinacademy.bff.api.operations.commentsservice.system.updatecomment.UpdateCommentBffOutput;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtDecoder;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.system.updatecomment.UpdateCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.hotel.getbyroomnumber.GetByRoomNumberOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SystemCommentsControllerTest {
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
    public SystemCommentsControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }



    @Test
    public void testUpdateCommentOk() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "ADMIN")
        );

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        UpdateCommentBffInput input = UpdateCommentBffInput.builder()
                .roomNo("roomNo")
                .content("content")
                .build();

        when(commentsRestExport.updateComment(UUID.randomUUID().toString(),
                UpdateCommentInput.builder()
                        .roomId(UUID.randomUUID().toString())
                        .content("content")
                        .build()))
                .thenReturn(UpdateCommentOutput.builder()
                        .id(UUID.randomUUID().toString())
                .build());

        when(hotelRestExport.getRoomByRoomNumber("roomNo"))
                .thenReturn(GetByRoomNumberOutput.builder()
                        .roomId(UUID.randomUUID().toString())
                        .build());

        mvc.perform(put(BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT, UUID.randomUUID().toString())
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCommentBadRequest() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "ADMIN")
        );

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        UpdateCommentBffInput input = UpdateCommentBffInput.builder()
                .roomNo("roomNo")
                .content("content")
                .build();

        when(commentsRestExport.updateComment(UUID.randomUUID().toString(),
                UpdateCommentInput.builder()
                        .roomId(UUID.randomUUID().toString())
                        .content("content")
                        .build()))
                .thenReturn(UpdateCommentOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        when(hotelRestExport.getRoomByRoomNumber("roomNo"))
                .thenReturn(GetByRoomNumberOutput.builder()
                        .roomId(UUID.randomUUID().toString())
                        .build());

        mvc.perform(put(BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT,"not-a-uuid")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCommentUnauthorized() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "ADMIN")
        );

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(put(BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UpdateCommentBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateCommentForbidden() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(put(BffRestApiRoutes.COMMENTS_API_SYSTEM_UPDATE_COMMENT, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UpdateCommentBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }
}
