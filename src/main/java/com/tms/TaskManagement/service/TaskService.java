package com.tms.TaskManagement.service;


import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(TaskDTO taskDTO);
    void deleteTask(Long id);
    Optional<TaskDTO> getTaskById(Long id);
    List<TaskDTO> getAllTasks();
    List<TaskDTO> getTasksByUserId(Long userId);
    List<TaskDTO> getTasksByTeamId(Long id);
    List<TaskDTO> getTasksByStatusAndUserId(String status, Long userId);
    TaskDTO updateTaskStatus(Long taskId, Task.TaskStatus newStatus);
    void markTaskAsDone(Long taskId);
    List<TaskDTO> getOverdueTasks();
    List<TaskDTO> getPersonalTasks(Long userId);
    TaskDTO createPersonalTask(Long userId, TaskDTO taskDTO);




}
