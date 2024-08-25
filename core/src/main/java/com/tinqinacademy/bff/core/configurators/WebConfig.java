package com.tinqinacademy.bff.core.configurators;

import com.tinqinacademy.bff.core.converters.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AdminEditCommentRequestToAdminEditCommentInput adminEditCommentRequestToAdminEditCommentInput;
    private final BookRoomRequestToBookRoomInput bookRoomRequestToBookRoomInput;
    private final CommentsOutputToCommentsResponse commentsOutputToCommentsResponse;
    private final CreateCommentRequestToCreateCommentInput createCommentRequestToCreateCommentInput;
    private final CreateRoomRequestToCreateRoomInput createRoomRequestToCreateRoomInput;
    private final DeleteCommentOutputToDeleteCommentResponse deleteCommentOutputToDeleteCommentResponse;
    private final DeleteRoomOutputToDeleteRoomResponse deleteRoomOutputToDeleteRoomResponse;
    private final GetRoomByIdOutputToGetRoomInfoResponse getRoomByIdOutputToGetRoomInfoResponse;
    private final HotelVisitorOutputToHotelVisitorResponse hotelVisitorOutputToHotelVisitorResponse;
    private final HotelVisitorRequestToHotelVisitorInput hotelVisitorRequestToHotelVisitorInput;
    private final PartialUpdateRoomRequestToPartialUpdateRoomInput partialUpdateRoomRequestToPartialUpdateRoomInput;
    private final RegisterVisitorRequestToRegisterVisitorInput registerVisitorRequestToRegisterVisitorInput;
    private final UpdateRoomRequestToUpdateRoomInput updateRoomRequestToUpdateRoomInput;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(adminEditCommentRequestToAdminEditCommentInput);
        registry.addConverter(bookRoomRequestToBookRoomInput);
        registry.addConverter(commentsOutputToCommentsResponse);
        registry.addConverter(createCommentRequestToCreateCommentInput);
        registry.addConverter(createRoomRequestToCreateRoomInput);
        registry.addConverter(deleteCommentOutputToDeleteCommentResponse);
        registry.addConverter(deleteRoomOutputToDeleteRoomResponse);
        registry.addConverter(getRoomByIdOutputToGetRoomInfoResponse);
        registry.addConverter(hotelVisitorOutputToHotelVisitorResponse);
        registry.addConverter(hotelVisitorRequestToHotelVisitorInput);
        registry.addConverter(partialUpdateRoomRequestToPartialUpdateRoomInput);
        registry.addConverter(registerVisitorRequestToRegisterVisitorInput);
        registry.addConverter(updateRoomRequestToUpdateRoomInput);
    }
}
