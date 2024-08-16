package com.tinqinacademy.bff.api.operations.hotelservice.hotel.getroom;




import com.tinqinacademy.bff.api.base.OperationBffOutput;
import com.tinqinacademy.bff.api.model.BathroomType;
import com.tinqinacademy.bff.api.model.BedSize;
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
    private List<BedSize> bedSizes;
    private BathroomType bathroomType;
    private Integer bedCount;
    private List<LocalDate> datesOccupied;
}