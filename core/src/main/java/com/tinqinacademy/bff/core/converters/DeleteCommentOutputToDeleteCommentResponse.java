package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.deletecomment.DeleteCommentResponse;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentOutputToDeleteCommentResponse extends AbstractConverter<DeleteCommentOutput, DeleteCommentResponse> {
    @Override
    protected Class<DeleteCommentResponse> getTargetClass() {
        return DeleteCommentResponse.class;
    }

    @Override
    protected DeleteCommentResponse doConvert(DeleteCommentOutput source) {
        DeleteCommentResponse result = DeleteCommentResponse.builder()
                .comment(source.getComment())
                .userId(source.getUserId())
                .editedByUserId(source.getEditedByUserId())
                .lastEditTime(source.getLastEditTime())
                .publishedDate(source.getPublishedDate())
                .roomId(source.getRoomId())
                .build();

        return result;
    }
}
