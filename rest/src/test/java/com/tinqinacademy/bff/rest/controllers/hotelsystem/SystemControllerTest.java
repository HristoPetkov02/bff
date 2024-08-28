package com.tinqinacademy.bff.rest.controllers.hotelsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.auth.api.operations.validatejwt.ValidateJwtOutput;
import com.tinqinacademy.auth.restexport.AuthRestExport;
import com.tinqinacademy.bff.api.model.input.VisitorRegisterBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.api.restroutes.BffRestApiRoutes;
import com.tinqinacademy.bff.rest.security.JwtDecoder;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsOutput;
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

}
