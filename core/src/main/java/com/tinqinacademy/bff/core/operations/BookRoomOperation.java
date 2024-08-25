package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.authentication.api.operations.getuser.GetUserInput;
import com.tinqinacademy.authentication.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomResponse;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
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
public class BookRoomOperation extends BaseOperation implements BookRoom {
    private final HotelClient hotelClient;
    private final AuthenticationClient authenticationClient;

    public BookRoomOperation(ConversionService conversionService,
                             ErrorMapper errorMapper,
                             HotelClient hotelClient,
                             AuthenticationClient authenticationClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Either<ErrorWrapper, BookRoomResponse> process(BookRoomRequest request) {
        return Try.of(() -> {
            log.info("start process input: {}", request);

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            log.info("username: {}", username);

            GetUserInput getUserInput = GetUserInput.builder()
                    .username(username)
                    .build();

            GetUserOutput getUserOutput = authenticationClient.getUser(getUserInput);

            BookRoomInput input = conversionService.convert(request, BookRoomInput.class);
            input.setUserId(getUserOutput.getUserId());

            BookRoomOutput output = hotelClient.bookRoom(input.getRoomId(), input);

            BookRoomResponse result = BookRoomResponse.builder()
                    .bookingId(output.getBookingId())
                    .build();

            log.info("end process result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        //validateCase(throwable, HttpStatus.BAD_REQUEST),
                        feignCase(throwable)
                ));
    }
}
