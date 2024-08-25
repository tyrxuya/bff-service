package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoomRequest;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoomResponse;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class DeleteRoomOperation extends BaseOperation implements DeleteRoom {
    private final HotelClient hotelClient;

    public DeleteRoomOperation(ConversionService conversionService,
                               ErrorMapper errorMapper,
                               HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, DeleteRoomResponse> process(DeleteRoomRequest request) {
        return Try.of(() -> {
            log.info("Start process method in DeleteRoomOperation. Input: {}", request);

            DeleteRoomOutput output = hotelClient.deleteRoom(request.getRoomId());

            DeleteRoomResponse result = conversionService.convert(output, DeleteRoomResponse.class);

            log.info("End process method in DeleteRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
