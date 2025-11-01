package com.amdox.taskmanagement.dto;

import com.amdox.taskmanagement.entity.Task;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200)
    private String title;
    
    @Size(max = 2000)
    private String description;
    
    @NotNull(message = "Priority is required")
    private Task.Priority priority;
    
    private Task.Status status;
    
    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    private LocalDateTime deadline;
    
    private Long assignedToId;
    
    private String category = "General";
    
    private String tags;
}
