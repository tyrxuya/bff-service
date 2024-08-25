package com.tinqinacademy.bff.api.operations.checkrooms;

import com.tinqinacademy.bff.api.base.OperationRequest;
//import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
//import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsAvailabilityRequest implements OperationRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String bedSize;
    private Integer bedCount;
    private String bathroomType;
}
