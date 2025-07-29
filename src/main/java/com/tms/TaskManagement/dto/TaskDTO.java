package com.tms.TaskManagement.dto;

import com.tms.TaskManagement.entity.Task;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class TaskDTO {

    private Long id;
    @NotBlank(message = "task name is required")
    private String taskName;

    @NotBlank(message = "task description is required")
    private String taskDescription;

    @NotBlank(message = "task status is required")
    private Task.TaskStatus status;

    private Long userId; //added this to take userId as input

    @NotBlank(message = "task due date is required")
    private LocalDateTime dueDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskDTO() {}

    public TaskDTO(Long id, String taskName, String taskDescription, Task.TaskStatus status, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.id = id;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Task.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(Task.TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
