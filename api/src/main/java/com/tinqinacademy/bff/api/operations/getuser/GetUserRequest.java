package com.tinqinacademy.bff.api.operations.getuser;

import com.tinqinacademy.bff.api.base.OperationRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserRequest implements OperationRequest {
    private String userId;
}
