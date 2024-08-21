package com.tinqinacademy.bff.core.processors.hotelservice.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.interfaces.ErrorHandlerService;
import com.tinqinacademy.bff.api.model.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffInput;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffOperation;
import com.tinqinacademy.bff.api.operations.hotelservice.system.report.ReportBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.report.ReportOutput;
import com.tinqinacademy.hotel.restexport.clients.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportBffOperationProcessor extends BaseOperationProcessor<ReportBffInput, ReportBffOutput> implements ReportBffOperation {
    private final HotelRestExport hotelRestExport;

    public ReportBffOperationProcessor(ConversionService conversionService, ObjectMapper mapper, ErrorHandlerService errorHandlerService, Validator validator, HotelRestExport hotelRestExport) {
        super(conversionService, mapper, errorHandlerService, validator);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, ReportBffOutput> process(ReportBffInput input) {
        return Try.of(() -> reportByCriteria(input))
                .toEither()
                .mapLeft(errorHandlerService::handle);
    }

    private ReportBffOutput reportByCriteria(ReportBffInput input) {
        logStart(input);
        validateInput(input);

        ReportOutput outputFromHotel = hotelRestExport.reportByCriteria(
                input.getStartDate(),
                input.getEndDate(),
                input.getFirstName(),
                input.getLastName(),
                input.getPhoneNo(),
                input.getIdCardNo(),
                input.getIdCardValidity(),
                input.getIdCardIssueAthority(),
                input.getIdCardIssueDate(),
                input.getRoomNo()
        );

        ReportBffOutput output = conversionService.convert(outputFromHotel, ReportBffOutput.class);
        logEnd(output);
        return output;
    }
}
