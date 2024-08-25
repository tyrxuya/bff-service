package com.tinqinacademy.bff.api.operations.usereditcomment;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserEditCommentResponse implements OperationResponse {
    private String commentId;
}
