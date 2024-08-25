package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsInfo;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsRequest;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsResponse;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.VisitorReport;
import com.tinqinacademy.bff.api.operations.hotelvisitor.HotelVisitorResponse;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.Match;

@Service
@Slf4j
public class GetRegisteredVisitorsInfoOperation extends BaseOperation implements GetRegisteredVisitorsInfo {
    private final HotelClient hotelClient;

    public GetRegisteredVisitorsInfoOperation(ConversionService conversionService,
                                              ErrorMapper errorMapper,
                                              HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, GetRegisteredVisitorsResponse> process(GetRegisteredVisitorsRequest request) {
        return Try.of(() -> {
            log.info("Start process method in GetRegisteredVisitorsInfoOperation. Input: {}", request);

            VisitorReport visitor = request.getVisitor();

            GetRegisteredVisitorsOutput output = hotelClient.getRegisteredVisitors(
                    request.getRoomNo(),
                    visitor.getFirstName(),
                    visitor.getLastName(),
                    visitor.getPhoneNo(),
                    visitor.getCivilNumber(),
                    visitor.getBirthday(),
                    visitor.getIdIssueAuthority(),
                    visitor.getIdIssueDate()
            );

            List<HotelVisitorResponse> hotelVisitors = output.getHotelVisitors()
                    .stream()
                    .map(hotelVisitor -> conversionService.convert(hotelVisitor, HotelVisitorResponse.class))
                    .toList();

            GetRegisteredVisitorsResponse result = GetRegisteredVisitorsResponse.builder()
                    .hotelVisitors(hotelVisitors)
                    .build();

            log.info("End process method in GetRegisteredVisitorsInfoOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
