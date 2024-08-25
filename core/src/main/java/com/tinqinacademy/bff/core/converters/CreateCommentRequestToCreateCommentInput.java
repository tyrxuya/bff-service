package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.createcomment.CreateCommentRequest;
import com.tinqinacademy.comments.api.operations.createcomment.CreateCommentInput;
import org.springframework.stereotype.Component;

@Component
public class CreateCommentRequestToCreateCommentInput extends AbstractConverter<CreateCommentRequest, CreateCommentInput> {
    @Override
    protected Class<CreateCommentInput> getTargetClass() {
        return CreateCommentInput.class;
    }

    @Override
    protected CreateCommentInput doConvert(CreateCommentRequest source) {
        CreateCommentInput result = CreateCommentInput.builder()
                .roomId(source.getRoomId())
                .content(source.getContent())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();

        return result;
    }
}
