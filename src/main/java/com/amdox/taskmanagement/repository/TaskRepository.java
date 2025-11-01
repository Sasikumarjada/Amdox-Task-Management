package com.amdox.taskmanagement.repository;

import com.amdox.taskmanagement.entity.Task;
import com.amdox.taskmanagement.entity.Task.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);
    List<Task> findByCreatedById(Long userId);
    List<Task> findByStatus(Status status);
    List<Task> findByDeadlineBefore(LocalDateTime deadline);
    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = ?1 AND t.status = ?2")
    List<Task> findByAssignedToIdAndStatus(Long userId, Status status);
    
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = ?1 AND t.deadline BETWEEN ?2 AND ?3")
    List<Task> findUpcomingTasksByUser(Long userId, LocalDateTime start, LocalDateTime end);
}
