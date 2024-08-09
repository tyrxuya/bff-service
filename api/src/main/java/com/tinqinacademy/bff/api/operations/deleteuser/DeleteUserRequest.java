package com.tinqinacademy.bff.api.operations.deleteuser;

import com.tinqinacademy.bff.api.base.OperationRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteUserRequest implements OperationRequest {
    private String userId;
}
