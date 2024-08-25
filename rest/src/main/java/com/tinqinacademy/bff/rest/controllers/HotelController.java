package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomRequest;
import com.tinqinacademy.bff.api.operations.bookroom.BookRoomResponse;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomAvailability;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomsAvailabilityRequest;
import com.tinqinacademy.bff.api.operations.checkrooms.CheckRoomsAvailabilityResponse;
import com.tinqinacademy.bff.api.operations.createcomment.CreateComment;
import com.tinqinacademy.bff.api.operations.createcomment.CreateCommentRequest;
import com.tinqinacademy.bff.api.operations.createcomment.CreateCommentResponse;
import com.tinqinacademy.bff.api.operations.getcomments.GetComments;
import com.tinqinacademy.bff.api.operations.getcomments.GetCommentsRequest;
import com.tinqinacademy.bff.api.operations.getcomments.GetCommentsResponse;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfo;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfoRequest;
import com.tinqinacademy.bff.api.operations.getroombyid.GetRoomInfoResponse;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoom;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoomRequest;
import com.tinqinacademy.bff.api.operations.unbookroom.UnbookRoomResponse;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditComment;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditCommentRequest;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditCommentResponse;
import com.tinqinacademy.comments.api.CommentsRestApiPaths;
import com.tinqinacademy.hotel.api.HotelRestApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Tag(name = "Hotel REST APIs")
@RequiredArgsConstructor
public class HotelController extends BaseController {
    private final BookRoom bookRoomOperation;
    private final CheckRoomAvailability checkRoomAvailabilityOperation;
    private final GetRoomInfo getRoomInfoOperation;
    private final UnbookRoom unbookRoomOperation;
    private final GetComments getCommentsOperation;
    private final CreateComment createCommentOperation;
    private final UserEditComment userEditCommentOperation;

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
        Either<ErrorWrapper, BookRoomResponse> result = bookRoomOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(HotelRestApiPaths.CHECK_ROOM)
    @Operation(
            summary = "Check room REST API",
            description = "Checks whether a room is available for a certain period."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room is found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<?> checkRoomAvailability(@RequestParam(required = false) @Schema(example = "2021-05-22") LocalDate startDate,
                                                   @RequestParam(required = false) @Schema(example = "2021-05-25") LocalDate endDate,
                                                   @RequestParam(required = false) @Schema(example = "kingSize") String bedSize,
                                                   @RequestParam(required = false) @Schema(example = "private") String bathroomType,
                                                   @RequestParam(required = false) @Schema(example = "2") Integer bedCount) {
        CheckRoomsAvailabilityRequest request = CheckRoomsAvailabilityRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .bedSize(bedSize)
                .bathroomType(bathroomType)
                .bedCount(bedCount)
                .build();

        Either<ErrorWrapper, CheckRoomsAvailabilityResponse> result = checkRoomAvailabilityOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(HotelRestApiPaths.GET_ROOM_INFO)
    @Operation(
            summary = "Info room REST API",
            description = "Returns basic info for a room with a specified id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    public ResponseEntity<?> getRoomInfo(@PathVariable @Schema(example = "15") String roomId) {
        GetRoomInfoRequest request = GetRoomInfoRequest.builder()
                .roomId(roomId)
                .build();

        Either<ErrorWrapper, GetRoomInfoResponse> result = getRoomInfoOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(HotelRestApiPaths.UNBOOK_ROOM)
    @Operation(
            summary = "Unbook room REST API",
            description = "Unbooks a room that the user has already been booked."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room unbooked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<?> unbookRoom(@PathVariable @Schema(example = "15") String bookingId) {
        UnbookRoomRequest request = UnbookRoomRequest.builder()
                .bookingId(bookingId)
                .build();

        Either<ErrorWrapper, UnbookRoomResponse> result = unbookRoomOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }



    @GetMapping(CommentsRestApiPaths.GET_COMMENT)
    @Operation(
            summary = "Get comments REST API",
            description = "Get list of all comments left for a certain room"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Comments not found")
    })
    public ResponseEntity<?> getComments(@PathVariable String roomId) {
        GetCommentsRequest request = GetCommentsRequest.builder()
                .roomId(roomId)
                .build();

        Either<ErrorWrapper, GetCommentsResponse> result = getCommentsOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(CommentsRestApiPaths.CREATE_COMMENT)
    @Operation(
            summary = "Create comment REST API",
            description = "Leaves a comment regarding a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createComment(@PathVariable String roomId,
                                           @RequestBody CreateCommentRequest request) {
        request.setRoomId(roomId);

        Either<ErrorWrapper, CreateCommentResponse> result = createCommentOperation.process(request);

        return getResponseEntity(result, HttpStatus.CREATED);
    }

    @PatchMapping(CommentsRestApiPaths.USER_EDIT_COMMENT)
    @Operation(
            summary = "User edit comment REST API",
            description = "User can edit own comment left for a certain room. Last edited date is updated. Info regarding user edited is updated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    public ResponseEntity<?> userEditComment(@PathVariable String commentId,
                                             @RequestBody UserEditCommentRequest request) {
        request.setCommentId(commentId);

        Either<ErrorWrapper, UserEditCommentResponse> result = userEditCommentOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

}
