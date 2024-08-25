package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.authentication.api.operations.getuser.GetUserInput;
import com.tinqinacademy.authentication.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoomRequest;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoomResponse;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class UnbookRoomOperation extends BaseOperation implements UnbookRoom {
    private final HotelClient hotelClient;
    private final AuthenticationClient authenticationClient;

    public UnbookRoomOperation(ConversionService conversionService,
                               ErrorMapper errorMapper,
                               HotelClient hotelClient,
                               AuthenticationClient authenticationClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Either<ErrorWrapper, UnbookRoomResponse> process(UnbookRoomRequest request) {
        return Try.of(() -> {
            log.info("Start process method in UnbookRoomOperation. Input: {}", request);

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            GetUserInput getUserInput = GetUserInput.builder()
                    .username(username)
                    .build();

            GetUserOutput getUserOutput = authenticationClient.getUser(getUserInput);

            UnbookRoomInput input = UnbookRoomInput.builder()
                    .bookingId(request.getBookingId())
                    .userId(getUserOutput.getUserId())
                    .build();

            UnbookRoomOutput output = hotelClient.unbookRoom(request.getBookingId(), input);

            UnbookRoomResponse result = UnbookRoomResponse.builder()
                    .bookingId(output.getBookingId())
                    .build();

            log.info("End process method in UnbookRoomOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
