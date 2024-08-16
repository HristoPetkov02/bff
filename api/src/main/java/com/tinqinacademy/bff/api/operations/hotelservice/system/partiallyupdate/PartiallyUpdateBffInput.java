package com.tinqinacademy.bff.api.operations.hotelservice.system.partiallyupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationBffInput;
import com.tinqinacademy.bff.api.validation.annotations.ValidBathroomType;
import com.tinqinacademy.bff.api.validation.annotations.ValidBedSize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PartiallyUpdateBffInput implements OperationBffInput {
    @JsonIgnore
    @UUID(message = "Room ID must be a valid UUID")
    private String roomId;

    @Min(value = 1, message = "Bed count must be at least 1")
    @Max(value = 10, message = "Bed count must be less than 10")
    private Integer bedCount;

    private List<@ValidBedSize String> bedSizes;

    @ValidBathroomType
    private String bathroomType;
    private String roomNo;

    @Positive(message = "Price must be positive")
    private BigDecimal price;
}
