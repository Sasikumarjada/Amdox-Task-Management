package com.amdox.taskmanagement.service;

import com.amdox.taskmanagement.entity.Task;
import com.amdox.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
    
    private final TaskRepository taskRepository;
    private final EmailService emailService;
    
    // Run every day at 9 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void sendDeadlineReminders() {
        log.info("Running deadline reminder scheduler...");
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        
        // Get tasks due within next 24 hours
        List<Task> upcomingTasks = taskRepository.findByDeadlineBetween(now, tomorrow);
        
        for (Task task : upcomingTasks) {
            if (task.getStatus() != Task.Status.COMPLETED && task.getAssignedTo() != null) {
                emailService.sendDeadlineReminderEmail(
                        task.getAssignedTo().getEmail(),
                        task
                );
                log.info("Reminder sent for task: {} to {}", 
                        task.getTitle(), 
                        task.getAssignedTo().getEmail());
            }
        }
        
        log.info("Deadline reminder scheduler completed. Sent {} reminders.", upcomingTasks.size());
    }
}
