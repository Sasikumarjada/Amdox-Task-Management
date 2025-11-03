package com.amdox.taskmanagement.controller;

import com.amdox.taskmanagement.dto.CommentRequest;
import com.amdox.taskmanagement.dto.CommentResponse;
import com.amdox.taskmanagement.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {
    
    private final CommentService commentService;
    
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @Valid @RequestBody CommentRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(commentService.addComment(request, authentication.getName()));
    }
    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Authentication authentication) {
        commentService.deleteComment(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
