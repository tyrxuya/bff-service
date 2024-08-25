package com.tinqinacademy.bff.api.operations.partialupdateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.tinqinacademy.bff.api.base.OperationRequest;
//import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
//import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PartialUpdateRoomRequest implements OperationRequest {
    @JsonIgnore
    private String roomId;

    private List<String> bedSizes;
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
