package com.tinqinacademy.bff.api.operations.comments;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentsResponse implements OperationResponse {
    private String commentId;
    private String content;
    private LocalDate publishDate;
    private LocalDate lastEditedDate;
    private String lastEditedBy;
    private String userId;
}
