package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditComment;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditCommentRequest;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditCommentResponse;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoomRequest;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoomResponse;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteComment;
import com.tinqinacademy.bff.api.operations.createroom.CreateRoom;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteCommentRequest;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteCommentResponse;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoom;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoomRequest;
import com.tinqinacademy.bff.api.operations.deleteroom.DeleteRoomResponse;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsInfo;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsRequest;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.GetRegisteredVisitorsResponse;
import com.tinqinacademy.bff.api.operations.getregisteredvisitors.VisitorReport;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoom;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.partialupdateroom.PartialUpdateRoomResponse;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitor;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitorRequest;
import com.tinqinacademy.bff.api.operations.registervisitor.RegisterVisitorResponse;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoom;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoomRequest;
import com.tinqinacademy.bff.api.operations.updateroom.UpdateRoomResponse;
import com.tinqinacademy.comments.api.CommentsRestApiPaths;
import com.tinqinacademy.hotel.api.HotelRestApiPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Tag(name = "System REST APIs")
@RequiredArgsConstructor
public class SystemController extends BaseController {
    private final RegisterVisitor registerVisitorOperation;
    private final GetRegisteredVisitorsInfo getRegisteredVisitorsInfoOperation;
    private final CreateRoom createRoomOperation;
    private final UpdateRoom updateRoomOperation;
    private final PartialUpdateRoom partialUpdateRoomOperation;
    private final DeleteRoom deleteRoomOperation;
    private final AdminEditComment adminEditCommentOperation;
    private final DeleteComment deleteCommentOperation;

    @PostMapping(HotelRestApiPaths.REGISTER_VISITOR)
    @Operation(
            summary = "Register visitor REST API",
            description = "Registers a visitor as room renter."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> registerVisitor(@PathVariable String bookingId,
                                             @RequestBody RegisterVisitorRequest request) {
        request.setBookingId(bookingId);

        Either<ErrorWrapper, RegisterVisitorResponse> result = registerVisitorOperation.process(request);
        return getResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping(HotelRestApiPaths.GET_VISITORS_INFO)
    @Operation(
            summary = "Info visitor REST API",
            description = "Provides a report based on various criteria. Provides info when room was occupied and by whom. Can report when a user has occupied rooms."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitors found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Visitors not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> getVisitorsInfo(@RequestParam(required = false) @Schema(example = "101b") String roomNo,
                                             @RequestParam(required = false) @Schema(example = "vanio") String firstName,
                                             @RequestParam(required = false) @Schema(example = "georgiev") String lastName,
                                             @RequestParam(required = false) @Schema(example = "+359887839281") String phoneNo,
                                             @RequestParam(required = false) @Schema(example = "0348888888") String civilNumber,
                                             @RequestParam(required = false) @Schema(example = "2003-09-22") LocalDate birthday,
                                             @RequestParam(required = false) @Schema(example = "mvr varna") String idIssueAuthority,
                                             @RequestParam(required = false) @Schema(example = "2015-05-22") LocalDate idIssueDate) {
        VisitorReport visitor = VisitorReport.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .civilNumber(civilNumber)
                .idIssueAuthority(idIssueAuthority)
                .idIssueDate(idIssueDate)
                .birthday(birthday)
                .build();

        GetRegisteredVisitorsRequest request = GetRegisteredVisitorsRequest.builder()
                .roomNo(roomNo)
                .visitor(visitor)
                .build();

        Either<ErrorWrapper, GetRegisteredVisitorsResponse> result = getRegisteredVisitorsInfoOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(HotelRestApiPaths.CREATE_ROOM)
    @Operation(
            summary = "Create room REST API",
            description = "Admin creates a new room with the specified parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest request) {
        Either<ErrorWrapper, CreateRoomResponse> result = createRoomOperation.process(request);

        return getResponseEntity(result, HttpStatus.CREATED);
    }

    @PutMapping(HotelRestApiPaths.UPDATE_ROOM)
    @Operation(
            summary = "Update room REST API",
            description = "Admin updates the info regarding a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> updateRoom(@PathVariable String roomId,
                                        @RequestBody UpdateRoomRequest request) {
        request.setRoomId(roomId);

        Either<ErrorWrapper, UpdateRoomResponse> result = updateRoomOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @PatchMapping(HotelRestApiPaths.PARTIAL_UPDATE_ROOM)
    @Operation(
            summary = "Partially update room REST API",
            description = "Admin partial update of room data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room info updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> partialUpdateRoom(@PathVariable @Schema(example = "15") String roomId,
                                               @RequestBody PartialUpdateRoomRequest request) {
        request.setRoomId(roomId);

        Either<ErrorWrapper, PartialUpdateRoomResponse> result = partialUpdateRoomOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(HotelRestApiPaths.DELETE_ROOM)
    @Operation(
            summary = "Delete room REST API",
            description = "Deletes a room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> deleteRoom(@PathVariable String roomId) {
        DeleteRoomRequest request = DeleteRoomRequest.builder()
                .roomId(roomId)
                .build();

        Either<ErrorWrapper, DeleteRoomResponse> result = deleteRoomOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }



    @PutMapping(CommentsRestApiPaths.ADMIN_EDIT_COMMENT)
    @Operation(
            summary = "Admin edit comment REST API",
            description = "Admin can edit any comment left for a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> adminEditComment(@PathVariable String commentId,
                                              @RequestBody AdminEditCommentRequest request) {
        request.setCommentId(commentId);

        Either<ErrorWrapper, AdminEditCommentResponse> result = adminEditCommentOperation.process(request);
        return getResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(CommentsRestApiPaths.DELETE_COMMENT)
    @Operation(
            summary = "Delete comment REST API",
            description = "Admin can delete any comment left for a certain room."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
        DeleteCommentRequest input = DeleteCommentRequest.builder()
                .commentId(commentId)
                .build();

        Either<ErrorWrapper, DeleteCommentResponse> result = deleteCommentOperation.process(input);
        return getResponseEntity(result, HttpStatus.OK);
    }
}
