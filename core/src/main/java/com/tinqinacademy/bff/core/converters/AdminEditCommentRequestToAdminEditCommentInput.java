package com.tinqinacademy.bff.core.converters;

import com.tinqinacademy.bff.api.operations.admineditcomment.AdminEditCommentRequest;
import com.tinqinacademy.comments.api.operations.admineditcomment.AdminEditCommentInput;
import org.springframework.stereotype.Component;

@Component
public class AdminEditCommentRequestToAdminEditCommentInput extends AbstractConverter<AdminEditCommentRequest, AdminEditCommentInput> {
    @Override
    protected Class<AdminEditCommentInput> getTargetClass() {
        return AdminEditCommentInput.class;
    }

    @Override
    protected AdminEditCommentInput doConvert(AdminEditCommentRequest source) {
        AdminEditCommentInput result = AdminEditCommentInput.builder()
                .commentId(source.getCommentId())
                .roomId(source.getRoomId())
                .content(source.getContent())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();

        return result;
    }
}
