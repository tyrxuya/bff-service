package com.tinqinacademy.bff.api.operations.checkrooms;

import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CheckRoomsAvailabilityRequest implements OperationRequest {
    @Schema(example = "2022-05-22")
    private LocalDate startDate;

    @Schema(example = "2022-05-25")
    private LocalDate endDate;

    @Schema(example = "kingSize")
    @ValidBedSize(optional = true)
    private String bedSize;

    @Schema(example = "2")
    private Integer bedCount;

    @Schema(example = "private")
    @ValidBathroomType(optional = true)
    private String bathroomType;
}
