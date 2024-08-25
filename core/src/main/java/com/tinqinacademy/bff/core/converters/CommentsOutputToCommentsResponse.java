package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.comments.CommentsResponse;
import com.tinqinacademy.comments.api.operations.comments.CommentsOutput;
import org.springframework.stereotype.Component;

@Component
public class CommentsOutputToCommentsResponse extends AbstractConverter<CommentsOutput, CommentsResponse> {
    @Override
    protected Class<CommentsResponse> getTargetClass() {
        return CommentsResponse.class;
    }

    @Override
    protected CommentsResponse doConvert(CommentsOutput source) {
        CommentsResponse result = CommentsResponse.builder()
                .commentId(source.getCommentId())
                .content(source.getContent())
                .lastEditedBy(source.getLastEditedBy())
                .lastEditedDate(source.getLastEditedDate())
                .publishDate(source.getPublishDate())
                .userId(source.getUserId())
                .build();

        return result;
    }
}
