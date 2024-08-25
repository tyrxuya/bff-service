package com.tinqinacademy.bff.api.operations.registervisitor;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class RegisterVisitorResponse implements OperationResponse {
    private List<String> visitorIds;
}
