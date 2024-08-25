package com.tinqinacademy.bff.core.operations;

import com.tinqinacademy.bff.api.errors.ErrorMapper;
import com.tinqinacademy.bff.api.errors.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteComment;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteCommentRequest;
import com.tinqinacademy.bff.api.operations.deletecomment.DeleteCommentResponse;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import static io.vavr.API.Match;

@Service
@Slf4j
public class DeleteCommentOperation extends BaseOperation implements DeleteComment {
    private final CommentsClient commentsClient;

    public DeleteCommentOperation(ConversionService conversionService,
                                  ErrorMapper errorMapper,
                                  CommentsClient commentsClient) {
        super(conversionService, errorMapper);
        this.commentsClient = commentsClient;
    }

    @Override
    public Either<ErrorWrapper, DeleteCommentResponse> process(DeleteCommentRequest request) {
        return Try.of(() -> {
            log.info("Start process method in DeleteCommentOperation. Input: {}", request);

            DeleteCommentOutput output = commentsClient.deleteComment(request.getCommentId());

            DeleteCommentResponse result = conversionService.convert(output, DeleteCommentResponse.class);

            log.info("End process method in DeleteCommentOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        feignCase(throwable)
                ));
    }
}
