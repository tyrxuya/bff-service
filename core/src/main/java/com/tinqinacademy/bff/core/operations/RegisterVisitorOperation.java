package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitor;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitorRequest;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitorResponse;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class RegisterVisitorOperation extends BaseOperation implements RegisterVisitor {
    private final HotelClient hotelClient;

    public RegisterVisitorOperation(ConversionService conversionService,
                                    ErrorMapper errorMapper,
                                    HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, RegisterVisitorResponse> process(RegisterVisitorRequest request) {
        return Try.of(() -> {
            log.info("Start process method in RegisterVisitorOperation. Input: {}", request);

            RegisterVisitorInput input = conversionService.convert(request, RegisterVisitorInput.class);

            RegisterVisitorOutput output = hotelClient.registerVisitor(request.getBookingId(), input);

            RegisterVisitorResponse result = RegisterVisitorResponse.builder()
                    .visitorIds(output.getVisitorIds())
                    .build();

            log.info("End process method in RegisterVisitorOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
