package com.tinqinacademy.bff.api.operations.hotelvisitor;

import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class HotelVisitorRequest implements OperationRequest {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private LocalDate birthday;
    private String civilNumber;
    private String idIssueAuthority;
    private LocalDate idIssueDate;
    private LocalDate idValidity;
}
