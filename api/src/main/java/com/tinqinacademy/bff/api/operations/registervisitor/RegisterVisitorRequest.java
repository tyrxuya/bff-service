package com.tinqinacademy.bff.api.operations.registervisitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.bff.api.operations.hotelvisitor.HotelVisitorRequest;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorRequest implements OperationRequest {
    @JsonIgnore
    private String bookingId;

    private List<HotelVisitorRequest> hotelVisitors;
}
