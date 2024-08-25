package com.tinqinacademy.bff.api.operations.admineditcomment;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminEditCommentResponse implements OperationResponse {
    private String commentId;
}
