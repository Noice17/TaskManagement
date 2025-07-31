package com.tms.TaskManagement.service;


import com.tms.TaskManagement.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(TaskDTO taskDTO);
    void deleteTask(Long id);
    Optional<TaskDTO> getTaskById(Long id);
    List<TaskDTO> getAllTasks();
}
