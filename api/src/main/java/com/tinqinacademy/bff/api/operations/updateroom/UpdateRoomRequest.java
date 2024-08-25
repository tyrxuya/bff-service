package com.tinqinacademy.bff.api.operations.updateroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
//import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
//import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
@Setter
public class UpdateRoomRequest implements OperationRequest {
    @JsonIgnore
    private String roomId;

    private List<String> bedSizes;
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
}
