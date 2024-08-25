package com.tinqinacademy.bff.api.operations.admineditcomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class AdminEditCommentRequest implements OperationRequest {
    @JsonIgnore
    private String commentId;

    private String roomId;
    private String firstName;
    private String lastName;
    private String content;
}
