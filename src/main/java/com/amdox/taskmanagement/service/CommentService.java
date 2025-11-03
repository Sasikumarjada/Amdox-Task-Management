package com.amdox.taskmanagement.service;

import com.amdox.taskmanagement.dto.CommentRequest;
import com.amdox.taskmanagement.dto.CommentResponse;
import com.amdox.taskmanagement.entity.Comment;
import com.amdox.taskmanagement.entity.Notification;
import com.amdox.taskmanagement.entity.Task;
import com.amdox.taskmanagement.entity.User;
import com.amdox.taskmanagement.repository.CommentRepository;
import com.amdox.taskmanagement.repository.NotificationRepository;
import com.amdox.taskmanagement.repository.TaskRepository;
import com.amdox.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    
    @Transactional
    public CommentResponse addComment(CommentRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setTask(task);
        comment.setUser(user);
        
        Comment savedComment = commentRepository.save(comment);
        
        // Notify task creator and assignee
        if (task.getCreatedBy() != null && !task.getCreatedBy().getId().equals(user.getId())) {
            createNotification(task.getCreatedBy(), task, 
                    user.getFullName() + " commented on: " + task.getTitle());
        }
        
        if (task.getAssignedTo() != null && !task.getAssignedTo().getId().equals(user.getId())) {
            createNotification(task.getAssignedTo(), task, 
                    user.getFullName() + " commented on: " + task.getTitle());
        }
        
        return mapToResponse(savedComment);
    }
    
    public List<CommentResponse> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!comment.getUser().getId().equals(user.getId()) && user.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("You don't have permission to delete this comment");
        }
        
        commentRepository.delete(comment);
    }
    
    private void createNotification(User user, Task task, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTask(task);
        notification.setMessage(message);
        notification.setType(Notification.NotificationType.TASK_COMMENT);
        notificationRepository.save(notification);
    }
    
    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setTaskId(comment.getTask().getId());
        response.setUserId(comment.getUser().getId());
        response.setUserName(comment.getUser().getFullName());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }
}
