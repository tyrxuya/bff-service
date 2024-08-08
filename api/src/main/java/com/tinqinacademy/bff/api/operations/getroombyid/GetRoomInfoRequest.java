package com.tinqinacademy.bff.api.operations.getroombyid;

import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRoomInfoRequest implements OperationRequest {
    @Schema(example = "15")
    private String roomId;
}
