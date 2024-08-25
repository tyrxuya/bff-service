package com.tinqinacademy.bff.api.operations.getcomments;

import com.tinqinacademy.bff.api.base.OperationRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsRequest implements OperationRequest {
    private String roomId;
}
