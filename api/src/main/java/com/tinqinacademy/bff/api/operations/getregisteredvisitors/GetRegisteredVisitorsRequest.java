package com.tinqinacademy.bff.api.operations.getregisteredvisitors;

import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsRequest implements OperationRequest {
    @Valid
    private HotelVisitorInput visitor;

    @Schema(example = "100b")
    private String roomNo;
}
