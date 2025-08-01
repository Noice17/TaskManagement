package com.tms.TaskManagement.mapper;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.User;

public class TaskMapper {

    public static Task toEntity(TaskDTO dto){
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setTaskDescription(dto.getTaskDescription());
        task.setTaskType(dto.getTaskType());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        return task;
    }

    public static Task toEntity(TaskDTO dto, User user){
        Task task = new Task();
        task.setTaskName(dto.getTaskName());
        task.setTaskDescription(dto.getTaskDescription());
        task.setTaskType((dto.getTaskType()));
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : Task.TaskStatus.ADDED);
        task.setUser(user);
        return task;
    }

    public static TaskDTO toDTO(Task task){
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setTaskType(task.getTaskType());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        if (task.getUser() != null){
            dto.setUserId(task.getUser().getId());
            dto.setUserName(task.getUser().getUsername());
        }
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }
}
