package com.tinqinacademy.bff.api.operations.bookroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BookRoomResponse implements OperationResponse {
    private String bookingId;
}
