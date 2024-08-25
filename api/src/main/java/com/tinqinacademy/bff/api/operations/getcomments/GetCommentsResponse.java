package com.tinqinacademy.bff.api.operations.getcomments;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.bff.api.operations.comments.CommentsResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsResponse implements OperationResponse {
    private List<CommentsResponse> comments;
}
