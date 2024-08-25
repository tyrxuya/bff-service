package com.tinqinacademy.bff.api.operations.createroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateRoomResponse implements OperationResponse {
    private String roomId;
}
