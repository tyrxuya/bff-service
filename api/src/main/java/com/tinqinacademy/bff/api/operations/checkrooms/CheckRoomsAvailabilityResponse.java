package com.tinqinacademy.bff.api.operations.checkrooms;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsAvailabilityResponse implements OperationResponse {
    private List<UUID> idList;
}
