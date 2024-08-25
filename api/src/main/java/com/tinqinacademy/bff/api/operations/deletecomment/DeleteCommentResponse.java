package com.tinqinacademy.bff.api.operations.deletecomment;

import com.tinqinacademy.bff.api.base.OperationResponse;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DeleteCommentResponse implements OperationResponse {
    private String comment;
    private String userId;
    private String roomId;
    private LocalDateTime publishedDate;
    private LocalDateTime lastEditTime;
    private String editedByUserId;
}
