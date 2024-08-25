package com.tinqinacademy.bff.api.operations.createcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateCommentRequest implements OperationRequest {
    @JsonIgnore
    private String roomId;

    private String firstName;
    private String lastName;
    private String content;
}
