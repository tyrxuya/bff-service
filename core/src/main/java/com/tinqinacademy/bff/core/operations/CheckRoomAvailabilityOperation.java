package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomAvailability;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomsAvailabilityRequest;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomsAvailabilityResponse;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsInput;
import com.tinqinacademy.hotel.api.operations.checkrooms.CheckRoomsOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class CheckRoomAvailabilityOperation extends BaseOperation implements CheckRoomAvailability {
    private final HotelClient hotelClient;

    public CheckRoomAvailabilityOperation(ConversionService conversionService,
                                          ErrorMapper errorMapper,
                                          HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, CheckRoomsAvailabilityResponse> process(CheckRoomsAvailabilityRequest request) {
        return Try.of(() -> {
            log.info("Start process method in CheckRoomAvailabilityOperation. Input: {}", request);

            CheckRoomsOutput output = hotelClient.checkRooms(
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getBedSize(),
                    request.getBathroomType(),
                    request.getBedCount()
            );

            CheckRoomsAvailabilityResponse result = CheckRoomsAvailabilityResponse.builder()
                    .idList(output.getIdList())
                    .build();

            log.info("End process method in CheckRoomAvailabilityOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
