package com.tms.TaskManagement.service.implementation;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.exception.custom.TeamNotFoundException;
import com.tms.TaskManagement.exception.custom.UserNotFoundException;
import com.tms.TaskManagement.mapper.TaskMapper;
import com.tms.TaskManagement.repository.TaskRepository;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.TaskService;
import com.tms.TaskManagement.util.MessageUtil;
import com.tms.TaskManagement.util.NotificationsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final MessageUtil messageUtil;
    private final NotificationsUtil notificationsUtil;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, TeamRepository teamRepository, MessageUtil messageUtil, NotificationsUtil notificationsUtil) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.messageUtil = messageUtil;
        this.notificationsUtil = notificationsUtil;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        messageUtil.getMessage("error.user.not_found", taskDTO.getUserId())
                ));

        Team team = teamRepository.findById(user.getTeam().getId())
                .orElseThrow(() -> new TeamNotFoundException(
                        messageUtil.getMessage("team.not_found", user.getTeam().getId())
                ));

        Task task = TaskMapper.toEntity(taskDTO, user);
        Task saved = taskRepository.save(task);

        notifTask(user.getId(), team.getId(), saved.getId(), messageUtil.getMessage("task.created"), false);
        return TaskMapper.toDTO(saved);
    }

    @Override
    public TaskDTO createPersonalTask(Long userId, TaskDTO taskDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Task task = new Task();
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription("Personal Task");
        task.setTaskType(Task.TaskType.PERSONAL);
        task.setUser(user);
        task.setStatus(Task.TaskStatus.ADDED);
        task.setCreatedAt(LocalDateTime.now());
        task.setDueDate(null);

        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
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

        Task task = TaskMapper.toEntity(taskDTO, user);
        Task updated = taskRepository.save(task);
        return TaskMapper.toDTO(updated);
    }

    @Override
    public TaskDTO updateTaskStatus(Long taskId, Task.TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        messageUtil.getMessage("error.task.not_found", taskId)));

        task.setStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toDTO(updatedTask);
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
        return taskRepository.findById(id)
                .map(task -> {
                    TaskDTO dto = TaskMapper.toDTO(task);
                    dto.setProgressPercentage(calculateProgressPercentage(task.getCreatedAt(), task.getDueDate()));
                    return dto;
                });
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> {
                    TaskDTO dto = TaskMapper.toDTO(task);
                    dto.setProgressPercentage(calculateProgressPercentage(task.getCreatedAt(), task.getDueDate()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getPersonalTasks(Long userId) {
        List<Task> personalTasks = taskRepository.findByUserIdAndTaskType(userId, Task.TaskType.PERSONAL);
        return personalTasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskDTO> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        messageUtil.getMessage("error.user.not_found", userId)
                ));

        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(task -> {
                    TaskDTO dto = TaskMapper.toDTO(task);
                    dto.setProgressPercentage(calculateProgressPercentage(task.getCreatedAt(), task.getDueDate()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByStatusAndUserId(String status, Long userId) {
        List<Task> tasks = taskRepository.findByStatusAndUserId(status, userId);
        return tasks.stream()
                .map(task -> {
                    TaskDTO dto = TaskMapper.toDTO(task);
                    dto.setProgressPercentage(calculateProgressPercentage(task.getCreatedAt(), task.getDueDate()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByTeamId(Long id) {
        List<Task> tasks = taskRepository.findByUser_Team_Id(id);
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markTaskAsDone(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException(
                        messageUtil.getMessage("error.task.not_found", taskId)));

        task.setStatus(Task.TaskStatus.COMPLETED);
        taskRepository.save(task);
    }

    @Override
    public List<TaskDTO> getOverdueTasks() {
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now()) && task.getStatus() != Task.TaskStatus.COMPLETED)
                .collect(Collectors.toList());

        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public double calculateProgressPercentage(LocalDateTime createdAt, LocalDateTime dueDate) {
        if (createdAt == null || dueDate == null) return 0.0;

        long totalDuration = ChronoUnit.SECONDS.between(createdAt, dueDate);
        long elapsedDuration = ChronoUnit.SECONDS.between(createdAt, LocalDateTime.now());

        if (totalDuration <= 0) return 0.0;

        double progress = (double) elapsedDuration / totalDuration * 100;

        return Math.min(progress, 100.0);
    }

    private void notifTask(Long userId, Long teamId, Long taskId, String description, boolean read) {
        notificationsUtil.notif(userId, teamId, taskId, description, read);
    }
}
