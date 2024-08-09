package com.tinqinacademy.bff.api.operations.getuser;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class GetUserResponse implements OperationResponse {
    private String username;
    private String password;
    private LocalDate birthday;
    private String email;
    private String phone;
}
