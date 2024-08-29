package com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom;




import com.tinqinacademy.bff.api.base.OperationBffOutput;
import com.tinqinacademy.bff.api.model.BffBathroomType;
import com.tinqinacademy.bff.api.model.BffBedSize;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRoomBffOutput implements OperationBffOutput {
    private String id;
    private BigDecimal price;
    private Integer floor;
    private List<BffBedSize> bffBedSizes;
    private BffBathroomType bffBathroomType;
    private Integer bedCount;
    private List<LocalDate> datesOccupied;
}
