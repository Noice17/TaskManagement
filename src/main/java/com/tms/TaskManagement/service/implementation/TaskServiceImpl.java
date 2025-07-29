package com.tms.TaskManagement.service.implementation;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.exception.custom.UserNotFoundException;
import com.tms.TaskManagement.repository.TaskRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.TaskService;
import com.tms.TaskManagement.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    //changed this so only 1 @Autowire annotation needed if multiple dependency needed to wire
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, MessageUtil messageUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
//        User user = userRepository.findById(taskDTO.getUserId())
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        messageUtil.getMessage("error.user.not_found", taskDTO.getUserId())
                ));

        Task task = mapToEntity(taskDTO, user);
        Task saved = taskRepository.save(task);
        return mapToDTO(saved);
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        if (taskDTO.getId() == null || !taskRepository.existsById(taskDTO.getId())) {
            throw new IllegalArgumentException("Task with ID " + taskDTO.getId() + " does not exist.");
        }

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        messageUtil.getMessage("error.user.not_found", taskDTO.getUserId())
                ));

        Task task = mapToEntity(taskDTO, user);
        Task updated = taskRepository.save(task);
        return mapToDTO(updated);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Task mapToEntity(TaskDTO dto, User user) { //changed signature to accept User entity
        Task task = new Task();
        task.setId(dto.getId());
        task.setTaskName(dto.getTaskName());
        task.setTaskDescription(dto.getTaskDescription());
        task.setStatus(dto.getStatus());
        task.setDueDate(dto.getDueDate());
        task.setUser(user); //set user to the task

        return task;
    }

    private TaskDTO mapToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setUserId(task.getUser().getId()); //maps the User (entity) to userId (dto)
        dto.setCreatedAt(task.getCreatedAt()); //displays created at in response
        dto.setUpdatedAt(task.getUpdatedAt()); //displays updated at in response
        return dto;
    }
}
