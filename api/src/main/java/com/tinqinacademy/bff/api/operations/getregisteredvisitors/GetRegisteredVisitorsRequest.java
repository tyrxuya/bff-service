package com.tinqinacademy.bff.api.operations.getregisteredvisitors;

import com.tinqinacademy.bff.api.base.OperationRequest;
//import com.tinqinacademy.hotel.api.operations.hotelvisitor.HotelVisitorInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetRegisteredVisitorsRequest implements OperationRequest {
    private VisitorReport visitor;
    private String roomNo;
}
