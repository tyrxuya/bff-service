package com.tinqinacademy.bff.api.operations.getregisteredvisitors;

import com.tinqinacademy.bff.api.base.OperationResponse;
import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsResponse implements OperationResponse {
    private List<HotelVisitorOutput> hotelVisitors;
}
