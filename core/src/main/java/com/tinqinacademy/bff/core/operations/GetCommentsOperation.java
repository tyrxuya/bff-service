package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.comments.CommentsResponse;
import com.tinqinacademy.bff.api.operations.getcomments.GetComments;
import com.tinqinacademy.bff.api.operations.getcomments.GetCommentsRequest;
import com.tinqinacademy.bff.api.operations.getcomments.GetCommentsResponse;
import com.tinqinacademy.comments.api.operations.getcomments.GetCommentsOutput;
import com.tinqinacademy.comments.restexport.CommentsClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.Match;

@Service
@Slf4j
public class GetCommentsOperation extends BaseOperation implements GetComments {
    private final CommentsClient commentsClient;
    public GetCommentsOperation(ConversionService conversionService,
                                ErrorMapper errorMapper,
                                CommentsClient commentsClient) {
        super(conversionService, errorMapper);
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<ErrorWrapper, GetCommentsResponse> process(GetCommentsRequest request) {
        return Try.of(() -> {
            log.info("Start process method in GetCommentsOperation. Input: {}", request);

            GetCommentsOutput output = commentsClient.getComments(request.getRoomId());

            List<CommentsResponse> comments = output.getComments()
                    .stream()
                    .map(comment -> conversionService.convert(comment, CommentsResponse.class))
                    .toList();

            GetCommentsResponse result = GetCommentsResponse.builder()
                    .comments(comments)
                    .build();

            log.info("End process method in GetCommentsOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
