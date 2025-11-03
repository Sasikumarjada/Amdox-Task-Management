package com.amdox.taskmanagement.service;

import com.amdox.taskmanagement.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Async
    public void sendTaskAssignmentEmail(String toEmail, Task task) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("New Task Assigned: " + task.getTitle());
            message.setText(String.format(
                    "You have been assigned a new task:\n\n" +
                    "Title: %s\n" +
                    "Description: %s\n" +
                    "Priority: %s\n" +
                    "Deadline: %s\n\n" +
                    "Please log in to Amdox Task Management to view details.",
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getDeadline()
            ));
            
            mailSender.send(message);
            log.info("Task assignment email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", toEmail, e);
        }
    }
    
    @Async
    public void sendDeadlineReminderEmail(String toEmail, Task task) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Task Deadline Reminder: " + task.getTitle());
            message.setText(String.format(
                    "Reminder: Your task is approaching its deadline!\n\n" +
                    "Title: %s\n" +
                    "Deadline: %s\n" +
                    "Status: %s\n\n" +
                    "Please complete the task on time.",
                    task.getTitle(),
                    task.getDeadline(),
                    task.getStatus()
            ));
            
            mailSender.send(message);
            log.info("Deadline reminder email sent to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send deadline reminder email to: {}", toEmail, e);
        }
    }
}
