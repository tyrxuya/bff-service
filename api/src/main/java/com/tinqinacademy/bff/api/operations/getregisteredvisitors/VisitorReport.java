package com.tinqinacademy.bff.api.operations.getregisteredvisitors;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class VisitorReport {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private LocalDate birthday;
    private String civilNumber;
    private String idIssueAuthority;
    private LocalDate idIssueDate;
    private LocalDate idValidity;
}
