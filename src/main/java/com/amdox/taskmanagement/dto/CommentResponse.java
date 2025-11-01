package com.amdox.taskmanagement.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private Long taskId;
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;
}
