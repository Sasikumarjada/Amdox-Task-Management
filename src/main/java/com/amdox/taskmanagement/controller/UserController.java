package com.amdox.taskmanagement.controller;

import com.amdox.taskmanagement.entity.User;
import com.amdox.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Map<String, Object>> users = userRepository.findAll().stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", user.getId());
                    userMap.put("username", user.getUsername());
                    userMap.put("email", user.getEmail());
                    userMap.put("fullName", user.getFullName());
                    userMap.put("role", user.getRole().name());
                    userMap.put("enabled", user.isEnabled());
                    return userMap;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());
        userMap.put("fullName", user.getFullName());
        userMap.put("role", user.getRole().name());
        userMap.put("enabled", user.isEnabled());
        
        return ResponseEntity.ok(userMap);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<Map<String, Object>> updateUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String roleStr = request.get("role");
        User.Role role = User.Role.valueOf(roleStr);
        user.setRole(role);
        userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User role updated successfully");
        response.put("userId", user.getId());
        response.put("newRole", user.getRole().name());
        
        return ResponseEntity.ok(response);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        boolean enabled = request.get("enabled");
        user.setEnabled(enabled);
        userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User status updated successfully");
        response.put("userId", user.getId());
        response.put("enabled", user.isEnabled());
        
        return ResponseEntity.ok(response);
    }
}
