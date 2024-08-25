package com.tinqinacademy.bff.api.operations.deleteroom;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class DeleteRoomResponse implements OperationResponse {
    private String bathroomType;
    private Integer floor;
    private String roomNo;
    private BigDecimal price;
    private List<String> bedSizes;
}
