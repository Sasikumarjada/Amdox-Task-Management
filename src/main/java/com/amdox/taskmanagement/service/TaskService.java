package com.amdox.taskmanagement.service;

import com.amdox.taskmanagement.dto.TaskRequest;
import com.amdox.taskmanagement.dto.TaskResponse;
import com.amdox.taskmanagement.entity.Notification;
import com.amdox.taskmanagement.entity.Task;
import com.amdox.taskmanagement.entity.User;
import com.amdox.taskmanagement.repository.NotificationRepository;
import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    
    @Transactional
    public TaskResponse createTask(TaskRequest request, String currentUsername) {
        User creator = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(Task.Status.TODO);
        task.setDeadline(request.getDeadline());
        task.setCreatedBy(creator);
        task.setCategory(request.getCategory());
        task.setTags(request.getTags());
        
        if (request.getAssignedToId() != null) {
            User assignee = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignedTo(assignee);
            
            // Send notification
            createNotification(assignee, task, "New task assigned: " + task.getTitle(), 
                    Notification.NotificationType.TASK_ASSIGNED);
            
            // Send email
            emailService.sendTaskAssignmentEmail(assignee.getEmail(), task);
        }
        
        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }
    
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, String currentUsername) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check permissions
        if (!canModifyTask(task, currentUser)) {
            throw new RuntimeException("You don't have permission to modify this task");
        }
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setDeadline(request.getDeadline());
        task.setCategory(request.getCategory());
        task.setTags(request.getTags());
        
        if (request.getAssignedToId() != null && 
                (task.getAssignedTo() == null || !task.getAssignedTo().getId().equals(request.getAssignedToId()))) {
            User newAssignee = userRepository.findById(request.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignedTo(newAssignee);
            
            createNotification(newAssignee, task, "Task reassigned: " + task.getTitle(), 
                    Notification.NotificationType.TASK_ASSIGNED);
            emailService.sendTaskAssignmentEmail(newAssignee.getEmail(), task);
        }
        
        if (request.getStatus() == Task.Status.COMPLETED) {
            task.setCompletedAt(LocalDateTime.now());
            if (task.getAssignedTo() != null) {
                createNotification(task.getCreatedBy(), task, "Task completed: " + task.getTitle(), 
                        Notification.NotificationType.TASK_COMPLETED);
            }
        }
        
        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }
    
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponse> getMyTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByAssignedToId(user.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponse> getTasksByStatus(Task.Status status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return mapToResponse(task);
    }
    
    @Transactional
    public void deleteTask(Long taskId, String currentUsername) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!canDeleteTask(task, currentUser)) {
            throw new RuntimeException("You don't have permission to delete this task");
        }
        
        taskRepository.delete(task);
    }
    
    private boolean canModifyTask(Task task, User user) {
        return user.getRole() == User.Role.ADMIN || 
               task.getCreatedBy().getId().equals(user.getId()) ||
               (user.getRole() == User.Role.EDITOR && task.getAssignedTo() != null && 
                task.getAssignedTo().getId().equals(user.getId()));
    }
    
    private boolean canDeleteTask(Task task, User user) {
        return user.getRole() == User.Role.ADMIN || 
               task.getCreatedBy().getId().equals(user.getId());
    }
    
    private void createNotification(User user, Task task, String message, Notification.NotificationType type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTask(task);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);
    }
    
    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        response.setDeadline(task.getDeadline());
        response.setCategory(task.getCategory());
        response.setTags(task.getTags());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setCompletedAt(task.getCompletedAt());
        
        if (task.getAssignedTo() != null) {
            response.setAssignedToId(task.getAssignedTo().getId());
            response.setAssignedToName(task.getAssignedTo().getFullName());
        }
        
        if (task.getCreatedBy() != null) {
            response.setCreatedById(task.getCreatedBy().getId());
            response.setCreatedByName(task.getCreatedBy().getFullName());
        }
        
        return response;
    }
}
