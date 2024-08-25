package com.tinqinacademy.bff.api.operations.createroom;

import com.tinqinacademy.bff.api.base.OperationRequest;
//import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
//import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CreateRoomRequest implements OperationRequest {
    private List<String> bedSizes;
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
