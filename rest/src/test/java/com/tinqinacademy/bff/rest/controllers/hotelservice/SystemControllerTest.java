package com.tinqinacademy.bff.rest.controllers.hotelservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.api.operations.validatejwt.ValidateJwtOutput;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.api.model.BffBathroomType;
import com.tinqinacademy.bff.api.model.BffBedSize;
import com.tinqinacademy.bff.api.model.input.VisitorRegisterBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate.PartiallyUpdateBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtDecoder;
import com.tinqinacademy.hotel.api.model.output.VisitorReportOutput;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateInput;
import com.tinqinacademy.hotel.api.operations.system.partiallyupdate.PartiallyUpdateOutput;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsOutput;
import com.tinqinacademy.hotel.api.operations.system.report.ReportOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
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
public class SystemControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private HotelRestExport hotelRestExport;
    @MockBean
    private AuthRestExport authenticationRestExport;
    @MockBean
    private JwtDecoder jwtDecoder;


    @Autowired
    public SystemControllerTest(MockMvc mvc, ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }


    @Test
    public void testRegisterVisitorsCreated() throws Exception {
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


        List<VisitorRegisterBffInput> visitors = List.of(
                VisitorRegisterBffInput.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(6))
                        .phoneNo("123456789")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .idCardNo("123456789")
                        .idCardIssueAthority("Athority")
                        .idCardIssueDate(LocalDate.now())
                        .idCardValidity(LocalDate.now().plusYears(10))
                        .roomNo("101")
                        .build(),
                VisitorRegisterBffInput.builder()
                        .firstName("Doe")
                        .lastName("John")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(6))
                        .phoneNo("123456789")
                        .birthDate(LocalDate.of(1990, 1, 1))
                        .roomNo("101")
                        .build()
        );

        RegisterVisitorsBffInput input = RegisterVisitorsBffInput.builder()
                .visitorRegisterBffInputs(visitors)
                .build();

        RegisterVisitorsInput registerVisitorsInput = RegisterVisitorsInput.builder().build();

        when(hotelRestExport.registerVisitors(registerVisitorsInput))
                .thenReturn(RegisterVisitorsOutput.builder().build());


        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterVisitorsBadRequest() throws Exception {
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


        List<VisitorRegisterBffInput> visitors = List.of(
                VisitorRegisterBffInput.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .build()
        );

        RegisterVisitorsBffInput input = RegisterVisitorsBffInput.builder()
                .visitorRegisterBffInputs(visitors)
                .build();

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());

        input = RegisterVisitorsBffInput.builder()
                .visitorRegisterBffInputs(List.of())
                .build();

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegisterVisitorsUnauthorized() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(RegisterVisitorsBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterVisitorsForbidden() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_REGISTER_VISITOR)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(RegisterVisitorsBffInput.builder().build())))
                .andExpect(status().isForbidden());
    }



    @Test
    public void testAddRoomCreated() throws Exception {
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

        AddRoomBffInput input = AddRoomBffInput.builder()
                .roomNo("101")
                .bedCount(1)
                .bedSizes(List.of(BffBedSize.DOUBLE.toString()))
                .bathroomType(BffBathroomType.PRIVATE.toString())
                .floor(1)
                .price(BigDecimal.valueOf(111))
                .build();

        AddRoomInput addRoomInput = AddRoomInput.builder().build();

        when(hotelRestExport.addRoom(addRoomInput))
                .thenReturn(AddRoomOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddRoomBadRequest() throws Exception {
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

        AddRoomBffInput input = AddRoomBffInput.builder()
                .floor(1)
                .price(BigDecimal.valueOf(111))
                .build();

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddRoomUnauthorized() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";

        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(false)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(AddRoomBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddRoomForbidden() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(post(BffRestApiRoutes.HOTEL_API_SYSTEM_ADD_ROOM)
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(AddRoomBffInput.builder().build())))
                .andExpect(status().isForbidden());
    }



    @Test
    public void testSearchByCriteriaOk() throws Exception {
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

        when(hotelRestExport.reportByCriteria(
                LocalDate.now(),
                LocalDate.now().plusDays(6),
                "John",
                "Doe",
                "123456789",
                "123456789",
                LocalDate.now().plusYears(10),
                "Athority",
                LocalDate.now(),
                "101"))
                .thenReturn(ReportOutput.builder()
                        .visitors(List.of(
                                VisitorReportOutput.builder()
                                        .startDate(LocalDate.now())
                                        .endDate(LocalDate.now().plusDays(6))
                                        .firstName("John")
                                        .lastName("Doe")
                                        .phoneNo("123456789")
                                        .idCardNo("123456789")
                                        .idCardValidity(LocalDate.now().plusYears(10))
                                        .idCardIssueAthority("Athority")
                                        .idCardIssueDate(LocalDate.now())
                                        .roomNo("101")
                                        .build()
                        ))
                        .build()
                );

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT)
                        .header("Authorization", jwtToken)
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(6).toString())
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("phoneNo", "123456789")
                        .param("idCardNo", "123456789")
                        .param("idCardValidity", LocalDate.now().plusYears(10).toString())
                        .param("idCardIssueAthority", "Athority")
                        .param("idCardIssueDate", LocalDate.now().toString())
                        .param("roomNo", "101")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchByCriteriaBadRequest() throws Exception {
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

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT)
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchByCriteriaUnauthorized() throws Exception {
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

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT)
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testSearchByCriteriaForbidden() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(get(BffRestApiRoutes.HOTEL_API_SYSTEM_VISITOR_REPORT)
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }



    @Test
    public void testUpdateRoomOk() throws Exception {
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

        UpdateRoomBffInput input = UpdateRoomBffInput.builder()
                .roomNo("101")
                .bedCount(1)
                .bedSizes(List.of(BffBedSize.DOUBLE.toString()))
                .bathroomType(BffBathroomType.PRIVATE.toString())
                .price(BigDecimal.valueOf(111))
                .build();

        when(hotelRestExport.updateRoom("101", UpdateRoomInput.builder().build()))
                .thenReturn(UpdateRoomOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        mvc.perform(put(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRoomBadRequest() throws Exception {
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

        UpdateRoomBffInput input = UpdateRoomBffInput.builder()
                .build();

        when(hotelRestExport.updateRoom("101", UpdateRoomInput.builder().build()))
                .thenReturn(UpdateRoomOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        mvc.perform(put(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateRoomUnauthorized() throws Exception {
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

        mvc.perform(put(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UpdateRoomBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateRoomForbidden() throws Exception {
        String jwtToken = "Bearer mock-jwt-token";
        String userId = UUID.randomUUID().toString();


        when(jwtDecoder.decodeJwt(any(jwtToken.getClass()))).thenReturn(
                Map.of("sub", userId,
                        "role", "USER")
        );
        when(authenticationRestExport.validateJwt(jwtToken)).thenReturn(
                ValidateJwtOutput.builder()
                        .isValid(true)
                        .build());

        mvc.perform(put(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(UpdateRoomBffInput.builder().build())))
                .andExpect(status().isForbidden());
    }



    @Test
    public void testPartiallyUpdateRoomOk() throws Exception {
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


        PartiallyUpdateBffInput input = PartiallyUpdateBffInput.builder()
                .roomNo("101")
                .bedCount(1)
                .build();

        when(hotelRestExport.partiallyUpdate("101", PartiallyUpdateInput.builder().build()))
                .thenReturn(PartiallyUpdateOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        mvc.perform(patch(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json-patch+json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPartiallyUpdateRoomBadRequest() throws Exception {
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


        PartiallyUpdateBffInput input = PartiallyUpdateBffInput.builder()
                .roomNo("101")
                .bedCount(1)
                .build();

        when(hotelRestExport.partiallyUpdate("101", PartiallyUpdateInput.builder().build()))
                .thenReturn(PartiallyUpdateOutput.builder()
                        .id(UUID.randomUUID().toString())
                        .build());

        mvc.perform(patch(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json-patch+json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPartiallyUpdateRoomUnauthorized() throws Exception {
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



        mvc.perform(patch(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json-patch+json")
                        .content(objectMapper.writeValueAsString(PartiallyUpdateBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testPartiallyUpdateRoomForbidden() throws Exception {
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



        mvc.perform(patch(BffRestApiRoutes.HOTEL_API_SYSTEM_UPDATE_PARTIALLY_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json-patch+json")
                        .content(objectMapper.writeValueAsString(PartiallyUpdateBffInput.builder().build())))
                .andExpect(status().isUnauthorized());
    }



    @Test
    public void testDeleteRoomOk() throws Exception{
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

        when(hotelRestExport.deleteRoom(UUID.randomUUID().toString()))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM, UUID.randomUUID().toString())
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRoomBadRequest() throws Exception{
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

        when(hotelRestExport.deleteRoom(UUID.randomUUID().toString()))
                .thenReturn(DeleteRoomOutput.builder().build());

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteRoomUnauthorized() throws Exception{
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

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteRoomForbidden() throws Exception {
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

        mvc.perform(delete(BffRestApiRoutes.HOTEL_API_SYSTEM_DELETE_ROOM, "not-uuid")
                        .header("Authorization", jwtToken)
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }
}
