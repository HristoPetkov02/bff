package com.tinqinacademy.bff.api.operations.hotelservice.system.registervisitors;

import com.tinqinacademy.bff.api.base.OperationBffInput;
import com.tinqinacademy.bff.api.model.input.VisitorRegisterBffInput;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterVisitorsBffInput implements OperationBffInput {
    @NotEmpty(message = "The list of visitors must be at least 1")
    private List<@Valid VisitorRegisterBffInput> visitorRegisterBffInputs;
}
