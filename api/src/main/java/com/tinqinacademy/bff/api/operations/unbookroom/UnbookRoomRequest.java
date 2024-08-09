package com.tinqinacademy.bff.api.operations.unbookroom;

import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomRequest implements OperationRequest {
    @Schema(example = "100")
    private String bookingId;
}
