package com.tinqinacademy.bff.api.operations.createuser;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateUserResponse implements OperationResponse {
    private UUID userId;
}
