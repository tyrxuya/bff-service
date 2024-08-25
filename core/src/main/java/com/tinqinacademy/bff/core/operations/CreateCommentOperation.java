package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.authentication.api.operations.getuser.GetUserInput;
import com.tinqinacademy.authentication.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.createcomment.CreateComment;
import com.tinqinacademy.bff.api.operations.createcomment.CreateCommentRequest;
import com.tinqinacademy.bff.api.operations.createcomment.CreateCommentResponse;
import com.tinqinacademy.comments.api.operations.createcomment.CreateCommentInput;
import com.tinqinacademy.comments.api.operations.createcomment.CreateCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsClient;
import com.tinqinacademy.hotel.api.operations.getroombyid.GetRoomByIdInput;
import com.tinqinacademy.hotel.restexport.HotelClient;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class CreateCommentOperation extends BaseOperation implements CreateComment {
    private final HotelClient hotelClient;
    private final CommentsClient commentsClient;
    private final AuthenticationClient authenticationClient;

    public CreateCommentOperation(ConversionService conversionService,
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
    public Either<ErrorWrapper, CreateCommentResponse> process(CreateCommentRequest request) {
        return Try.of(() -> {
            log.info("Start process method in CreateCommentOperation. Input: {}", request);

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            GetUserInput getUserInput = GetUserInput.builder()
                    .username(username)
                    .build();

            GetUserOutput getUserOutput = authenticationClient.getUser(getUserInput);

            hotelClient.getRoomById(request.getRoomId()); //will throw if room not found

            CreateCommentInput commentInput = conversionService.convert(request, CreateCommentInput.class);
            commentInput.setUserId(getUserOutput.getUserId());

            CreateCommentOutput commentOutput = commentsClient.createComment(commentInput.getRoomId(), commentInput);

            CreateCommentResponse result = CreateCommentResponse.builder()
                    .commentId(commentOutput.getCommentId())
                    .build();

            log.info("End process method in CreateCommentOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
