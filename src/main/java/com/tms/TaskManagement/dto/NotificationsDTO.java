package com.tms.TaskManagement.dto;

import com.tms.TaskManagement.entity.Notifications;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class NotificationsDTO {

    private Long id;

    @NotBlank(message = "user id is required")
    private Long userId;

    @NotBlank(message = "team id is required")
    private Long teamId;

    @NotBlank(message = "task id is required")
    private Long taskId;

    @NotBlank(message = "description is required")
    private String description;

    private LocalDateTime createdAt;

    private boolean isRead;

    public NotificationsDTO() {}

    public NotificationsDTO(Long id, Long userId, Long teamId, Long taskId, String description, LocalDateTime createdAt, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.teamId = teamId;
        this.taskId = taskId;
        this.description = description;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
