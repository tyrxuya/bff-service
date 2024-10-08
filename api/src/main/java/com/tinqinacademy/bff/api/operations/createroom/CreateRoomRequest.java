package com.tinqinacademy.bff.api.operations.createroom;

import com.tinqinacademy.bff.api.base.OperationRequest;
import com.tinqinacademy.hotel.api.validators.bathroomtype.ValidBathroomType;
import com.tinqinacademy.hotel.api.validators.bedsize.ValidBedSize;
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
    private List<@ValidBedSize String> bedSizes;

    @Schema(example = "shared")
    @ValidBathroomType
    private String bathroomType;

    @Schema(example = "7")
    @Min(value = 1, message = "floor cannot be less than 1")
    @Max(value = 12, message = "floor cannot be greater than 12")
    private Integer floor;

    @Schema(example = "700b")
    @NotBlank(message = "room number cant be blank")
    private String roomNo;

    @Schema(example = "1838124.15")
    //@Positive(message = "price cannot be negative")
    @DecimalMin(value = "200", message = "price cannot be lower than 200")
    @Digits(integer = 4, fraction = 2, message = "invalid price decimal")
    private BigDecimal price;
}
