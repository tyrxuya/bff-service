package com.tinqinacademy.bff.api.operations.hotelvisitor;

import com.tinqinacademy.bff.api.base.OperationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class HotelVisitorResponse implements OperationResponse {
    @Schema(example = "vanio")
    private String firstName;

    @Schema(example = "georgiev")
    private String lastName;

    @Schema(example = "0889252012")
    private String phoneNo;

    @Schema(example = "2003-09-22")
    private LocalDate birthday;

    @Schema(example = "0349228888")
    private String civilNumber;

    @Schema(example = "mvr varna")
    private String idIssueAuthority;

    @Schema(example = "2015-05-22")
    private LocalDate idIssueDate;

    @Schema(example = "2027-12-12")
    private LocalDate idValidity;
}
