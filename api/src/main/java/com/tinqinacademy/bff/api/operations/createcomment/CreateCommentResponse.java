package com.tinqinacademy.bff.api.operations.createcomment;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateCommentResponse implements OperationResponse {
    private String commentId;
}
