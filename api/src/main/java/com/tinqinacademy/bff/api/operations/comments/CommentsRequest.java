package com.tinqinacademy.bff.api.operations.comments;

import com.tinqinacademy.bff.api.base.OperationRequest;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentsRequest implements OperationRequest {
    private String commentId;
    private String firstName;
    private String lastName;
    private String content;
    private LocalDateTime publishDate;
    private LocalDateTime lastEditedDate;
}
