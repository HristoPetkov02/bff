package com.tinqinacademy.bff.api.operations.hotelservice.system.report;

import com.tinqinacademy.bff.api.base.OperationBffOutput;
import com.tinqinacademy.bff.api.model.output.VisitorReportBffOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportBffOutput implements OperationBffOutput {
    private List<VisitorReportBffOutput> visitors;
}
