package com.amdox.taskmanagement.controller;

import com.amdox.taskmanagement.dto.TaskRequest;
import com.amdox.taskmanagement.dto.TaskResponse;
import com.amdox.taskmanagement.entity.Task;
import com.amdox.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(taskService.createTask(request, authentication.getName()));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(taskService.updateTask(id, request, authentication.getName()));
    }
    
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskResponse>> getMyTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getMyTasks(authentication.getName()));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable Task.Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
