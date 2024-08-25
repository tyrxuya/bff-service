package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfo;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfoRequest;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfoResponse;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class GetRoomInfoOperation extends BaseOperation implements GetRoomInfo {
    private final HotelClient hotelClient;

    public GetRoomInfoOperation(ConversionService conversionService,
                                ErrorMapper errorMapper,
                                HotelClient hotelClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
    }

    @Override
    public Either<ErrorWrapper, GetRoomInfoResponse> process(GetRoomInfoRequest request) {
        return Try.of(() -> {
            log.info("Start process method in GetRoomInfoOperation. Input: {}", request);

            GetRoomByIdOutput output = hotelClient.getRoomById(request.getRoomId());

            GetRoomInfoResponse result = conversionService.convert(output, GetRoomInfoResponse.class);

            log.info("End process method in GetRoomInfoOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
