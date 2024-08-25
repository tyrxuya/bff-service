package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoom;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoomRequest;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoomResponse;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
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
public class CreateRoomOperation extends BaseOperation implements CreateRoom {
    private final HotelClient hotelClient;

    public CreateRoomOperation(ConversionService conversionService,
                               ErrorMapper errorMapper,
                               HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, CreateRoomResponse> process(CreateRoomRequest request) {
        return Try.of(() -> {
            log.info("Start process method in CreateRoomOperation. Input: {}", request);

            CreateRoomInput input = conversionService.convert(request, CreateRoomInput.class);

            CreateRoomOutput output = hotelClient.createRoom(input);

            CreateRoomResponse result = CreateRoomResponse.builder()
                    .roomId(output.getRoomId())
                    .build();

            log.info("End process method in CreateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
