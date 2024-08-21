package com.tinqinacademy.bff.core.converters.hotelservice;

import com.tinqinacademy.bff.api.model.output.VisitorReportBffOutput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.model.output.VisitorReportOutput;
import com.tinqinacademy.hotel.api.operations.system.report.ReportOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ReportOutputToReportBffOutput extends BaseConverter<ReportOutput, ReportBffOutput> {
    @Override
    protected ReportBffOutput convertObject(ReportOutput input) {
        List<VisitorReportBffOutput> visitors = new ArrayList<>();
        for (VisitorReportOutput visitor : input.getVisitors()) {
            visitors.add(
                    VisitorReportBffOutput.builder()
                            .startDate(visitor.getStartDate())
                            .endDate(visitor.getEndDate())
                            .firstName(visitor.getFirstName())
                            .lastName(visitor.getLastName())
                            .phoneNo(visitor.getPhoneNo())
                            .idCardNo(visitor.getIdCardNo())
                            .idCardValidity(visitor.getIdCardValidity())
                            .idCardIssueAthority(visitor.getIdCardIssueAthority())
                            .idCardIssueDate(visitor.getIdCardIssueDate())
                            .roomNo(visitor.getRoomNo())
                            .build());
        }
        return ReportBffOutput.builder()
                .visitors(visitors)
                .build();
    }
}
