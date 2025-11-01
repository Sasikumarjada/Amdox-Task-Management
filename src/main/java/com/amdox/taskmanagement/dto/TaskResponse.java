package com.amdox.taskmanagement.dto;

import com.amdox.taskmanagement.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Task.Priority priority;
    private Task.Status status;
    private LocalDateTime deadline;
    private String category;
    private String tags;
    private Long assignedToId;
    private String assignedToName;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
