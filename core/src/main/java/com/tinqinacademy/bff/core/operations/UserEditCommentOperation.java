package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.authentication.api.operations.getuser.GetUserInput;
import com.tinqinacademy.authentication.api.operations.getuser.GetUserOutput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditComment;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditCommentRequest;
import com.tinqinacademy.bff.api.operations.usereditcomment.UserEditCommentResponse;
import com.tinqinacademy.comments.api.operations.usereditcomment.UserEditCommentInput;
import com.tinqinacademy.comments.api.operations.usereditcomment.UserEditCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class UserEditCommentOperation extends BaseOperation implements UserEditComment {
    private final CommentsClient commentsClient;
    private final AuthenticationClient authenticationClient;

    public UserEditCommentOperation(ConversionService conversionService,
                                    ErrorMapper errorMapper,
                                    CommentsClient commentsClient,
                                    AuthenticationClient authenticationClient) {
        super(conversionService, errorMapper);
        this.commentsClient = commentsClient;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Either<ErrorWrapper, UserEditCommentResponse> process(UserEditCommentRequest request) {
        return Try.of(() -> {
            log.info("Start process method in UserEditCommentOperation. Input: {}", request);

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            log.info("username: {}", username);

            GetUserInput getUserInput = GetUserInput.builder()
                    .username(username)
                    .build();
            log.info("got user input");

            GetUserOutput getUserOutput = authenticationClient.getUser(getUserInput);
            log.info("get user output: {}", getUserOutput);

            UserEditCommentInput input = UserEditCommentInput.builder()
                    .commentId(request.getCommentId())
                    .content(request.getContent())
                    .userId(getUserOutput.getUserId())
                    .build();
            log.info("user edit comment input: {}", input);

            UserEditCommentOutput output = commentsClient.userEditComment(request.getCommentId(), input);
            log.info("user edit comment output: {}", output);

            UserEditCommentResponse result = UserEditCommentResponse.builder()
                    .commentId(output.getCommentId())
                    .build();

            log.info("End process method in UserEditCommentOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
