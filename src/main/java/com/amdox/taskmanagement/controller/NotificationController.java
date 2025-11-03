package com.amdox.taskmanagement.controller;

import com.amdox.taskmanagement.entity.Notification;
import com.amdox.taskmanagement.entity.User;
import com.amdox.taskmanagement.repository.NotificationRepository;
import com.amdox.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {
    
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getMyNotifications(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Map<String, Object>> notifications = notificationRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::mapNotificationToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/unread")
    public ResponseEntity<List<Map<String, Object>>> getUnreadNotifications(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Map<String, Object>> notifications = notificationRepository
                .findByUserIdAndIsReadFalse(user.getId())
                .stream()
                .map(this::mapNotificationToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        long count = notificationRepository.countByUserIdAndIsReadFalse(user.getId());
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(@PathVariable Long id, Authentication authentication) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You don't have permission to mark this notification as read");
        }
        
        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
        
        return ResponseEntity.ok(mapNotificationToResponse(notification));
    }
    
    @PutMapping("/read-all")
    public ResponseEntity<Map<String, String>> markAllAsRead(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(user.getId());
        notifications.forEach(notification -> {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
        });
        notificationRepository.saveAll(notifications);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "All notifications marked as read");
        return ResponseEntity.ok(response);
    }
    
    private Map<String, Object> mapNotificationToResponse(Notification notification) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", notification.getId());
        map.put("message", notification.getMessage());
        map.put("type", notification.getType().name());
        map.put("isRead", notification.isRead());
        map.put("createdAt", notification.getCreatedAt());
        map.put("readAt", notification.getReadAt());
        
        if (notification.getTask() != null) {
            map.put("taskId", notification.getTask().getId());
            map.put("taskTitle", notification.getTask().getTitle());
        }
        
        return map;
    }
}
