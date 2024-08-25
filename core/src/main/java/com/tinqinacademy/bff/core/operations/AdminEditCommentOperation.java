package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.authentication.api.operations.getuser.GetUserInput;
import com.tinqinacademy.authentication.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditComment;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditCommentRequest;
import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditCommentResponse;
import com.tinqinacademy.comments.api.operations.admineditcomment.AdminEditCommentInput;
import com.tinqinacademy.comments.api.operations.admineditcomment.AdminEditCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsClient;
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
public class AdminEditCommentOperation extends BaseOperation implements AdminEditComment {
    private final HotelClient hotelClient;
    private final CommentsClient commentsClient;
    private final AuthenticationClient authenticationClient;

    public AdminEditCommentOperation(ConversionService conversionService,
                                     ErrorMapper errorMapper,
                                     HotelClient hotelClient,
                                     CommentsClient commentsClient,
                                     AuthenticationClient authenticationClient) {
        super(conversionService, errorMapper);
        this.hotelClient = hotelClient;
        this.commentsClient = commentsClient;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Either<ErrorWrapper, AdminEditCommentResponse> process(AdminEditCommentRequest request) {
        return Try.of(() -> {
            log.info("Start process method in AdminEditCommentOperation. Input: {}", request);

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            GetUserInput getUserInput = GetUserInput.builder()
                    .username(username)
                    .build();

            GetUserOutput getUserOutput = authenticationClient.getUser(getUserInput);

            hotelClient.getRoomById(request.getRoomId()); //will throw if room not found

            AdminEditCommentInput editInput = conversionService.convert(request, AdminEditCommentInput.class);
            editInput.setUserId(getUserOutput.getUserId());

            AdminEditCommentOutput editOutput = commentsClient.adminEditComment(editInput.getCommentId(), editInput);

            AdminEditCommentResponse result = AdminEditCommentResponse.builder()
                    .commentId(editOutput.getCommentId())
                    .build();

            log.info("End process method in AdminEditCommentOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
