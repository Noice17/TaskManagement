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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    //changed this so only 1 @Autowire annotation needed if multiple dependency needed to wire
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
//        User user = userRepository.findById(taskDTO.getUserId())
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
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(TaskMapper::toDTO);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void notifTask(Long userId, Long teamId, Long taskId, String description, boolean read) {
        notificationsUtil.notif(
                userId,
                teamId,
                taskId,
                description,
                read
        );
    }

}
