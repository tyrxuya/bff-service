package com.tinqinacademy.bff.api.operations.unbookroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UnbookRoomResponse implements OperationResponse {
    private String bookingId;
}
