package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomResponse;
import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class BookRoomOperation extends BaseOperation implements BookRoom {
    private final HotelRestExport hotelRestExport;


    public BookRoomOperation(Validator validator, ConversionService conversionService, ErrorMapper errorMapper, HotelRestExport hotelRestExport) {
        super(validator, conversionService, errorMapper);
        this.hotelRestExport = hotelRestExport;
    }

    @Override
    public Either<ErrorWrapper, BookRoomResponse> process(BookRoomRequest input) {
        return Try.of(() -> {
                    log.info("start process input: {}", input);

                    validate(input);

                    BookRoomOutput output = hotelRestExport.bookRoom(input.getRoomId(), conversionService.convert(input, BookRoomInput.class));

                    BookRoomResponse result = BookRoomResponse.builder()
                            .bookingId(output.getBookingId())
                            .build();

                    log.info("end process result: {}", result);

                    return result;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST),
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT)
                ));
    }
}
