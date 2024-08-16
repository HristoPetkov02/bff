package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.model.input.VisitorRegisterBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors.RegisterVisitorsBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.model.input.VisitorRegisterInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitors.RegisterVisitorsInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RegisterVisitorsBffInputToRegisterVisitorsInput extends BaseConverter<RegisterVisitorsBffInput, RegisterVisitorsInput> {
    @Override
    protected RegisterVisitorsInput convertObject(RegisterVisitorsBffInput input) {
        List<VisitorRegisterInput> visitorRegisterInputs = input.getVisitorRegisterBffInputs().stream()
                .map(visitorRegisterBffInput -> VisitorRegisterInput.builder()
                        .startDate(visitorRegisterBffInput.getStartDate())
                        .endDate(visitorRegisterBffInput.getEndDate())
                        .firstName(visitorRegisterBffInput.getFirstName())
                        .lastName(visitorRegisterBffInput.getLastName())
                        .phoneNo(visitorRegisterBffInput.getPhoneNo())
                        .birthDate(visitorRegisterBffInput.getBirthDate())
                        .idCardNo(visitorRegisterBffInput.getIdCardNo())
                        .idCardValidity(visitorRegisterBffInput.getIdCardValidity())
                        .idCardIssueAthority(visitorRegisterBffInput.getIdCardIssueAthority())
                        .idCardIssueDate(visitorRegisterBffInput.getIdCardIssueDate())
                        .roomNo(visitorRegisterBffInput.getRoomNo())
                        .build())
                .toList();

        RegisterVisitorsInput output = RegisterVisitorsInput.builder()
                .visitorRegisterInputs(visitorRegisterInputs)
                .build();
        return output;
    }
}
