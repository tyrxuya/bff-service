package com.tinqinacademy.bff.api.operations.partialupdateroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PartialUpdateRoomResponse implements OperationResponse {
    @Schema(example = "15")
    private String roomId;
}
