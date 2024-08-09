package com.tinqinacademy.bff.api.operations.updateroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateRoomResponse implements OperationResponse {
    @Schema(example = "15")
    private UUID roomId;
}
