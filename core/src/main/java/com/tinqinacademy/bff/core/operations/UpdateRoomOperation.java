package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoomResponse;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class UpdateRoomOperation extends BaseOperation implements UpdateRoom {
    private final HotelClient hotelClient;

    public UpdateRoomOperation(ConversionService conversionService,
                               ErrorMapper errorMapper,
                               HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, UpdateRoomResponse> process(UpdateRoomRequest request) {
        return Try.of(() -> {
            log.info("Start process method in UpdateRoomOperation. Input: {}", request);

            UpdateRoomInput input = conversionService.convert(request, UpdateRoomInput.class);

            UpdateRoomOutput output = hotelClient.updateRoom(request.getRoomId(), input);

            UpdateRoomResponse result = UpdateRoomResponse.builder()
                    .roomId(output.getRoomId())
                    .build();

            log.info("End process method in UpdateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
