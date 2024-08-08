package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomResponse;
import com.tinqinacademy.hotel.api.HotelRestApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Hotel REST APIs")
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final BookRoom bookRoom;

    @PostMapping(HotelRestApiPaths.BOOK_ROOM)
    @Operation(
            summary = "Book room REST API",
            description = "Books the room specified."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "400", description = "bad request"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    public ResponseEntity<?> bookRoom(@PathVariable String roomId,
                                      @RequestBody BookRoomRequest request) {
        request.setRoomId(roomId);
        Either<ErrorWrapper, BookRoomResponse> result = bookRoom.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

}
