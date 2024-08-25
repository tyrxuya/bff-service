package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoom;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoomResponse;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class PartialUpdateRoomOperation extends BaseOperation implements PartialUpdateRoom {
    private final HotelClient hotelClient;

    public PartialUpdateRoomOperation(ConversionService conversionService,
                                      ErrorMapper errorMapper,
                                      HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, PartialUpdateRoomResponse> process(PartialUpdateRoomRequest request) {
        return Try.of(() -> {
            log.info("Start process method in PartialUpdateRoomOperation. Input: {}", request);

            PartialUpdateRoomInput input = conversionService.convert(request, PartialUpdateRoomInput.class);

            PartialUpdateRoomOutput output = hotelClient.partialUpdateRoom(request.getRoomId(), input);

            PartialUpdateRoomResponse result = PartialUpdateRoomResponse.builder()
                    .roomId(output.getRoomId())
                    .build();

            log.info("End process method in PartialUpdateRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
