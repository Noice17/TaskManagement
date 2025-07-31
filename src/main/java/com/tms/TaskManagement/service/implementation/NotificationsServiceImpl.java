package com.tms.TaskManagement.service.implementation;

import com.tms.TaskManagement.dto.NotificationsDTO;
import com.tms.TaskManagement.entity.Notifications;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.mapper.NotificationsMapper;
import com.tms.TaskManagement.mapper.TaskMapper;
import com.tms.TaskManagement.repository.NotificationsRepository;
import com.tms.TaskManagement.repository.TaskRepository;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationsServiceImpl implements NotificationsService {


    private final NotificationsRepository notificationRepo;
    private final UserRepository userRepo;
    private final TeamRepository teamRepo;
    private final TaskRepository taskRepo;

    @Autowired
    public NotificationsServiceImpl(NotificationsRepository notificationRepo, UserRepository userRepo, TeamRepository teamRepo, TaskRepository taskRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.taskRepo = taskRepo;
    }

    @Override
    @Transactional
    public void recordNotifications(NotificationsDTO notificationsDTO) {
        User user = userRepo.findById(notificationsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("error.user.not_found" + notificationsDTO.getUserId()));
        Task task = taskRepo.findById(notificationsDTO.getTaskId())
                .orElseThrow(() -> new RuntimeException("error.task.not_found: " + notificationsDTO.getUserId()));
        Team team = teamRepo.findById(notificationsDTO.getTeamId())
                .orElseThrow(() -> new RuntimeException("error.team.not_found: " + notificationsDTO.getUserId()));

        Notifications notifications = NotificationsMapper.toEntity(notificationsDTO,user,team,task);
        notificationRepo.save(notifications);
    }

    @Override
    public List<NotificationsDTO> getAllNotifications() {
        return notificationRepo.findAll().stream()
                .map(NotificationsMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<NotificationsDTO> getNotificationsByUserId(Long userId) {
        return notificationRepo.findByUserId(userId)
                .stream()
                .map(NotificationsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationsDTO> getNotificationsByTeamId(Long teamId) {
        return notificationRepo.findByTeamId(teamId)
                .stream()
                .map(NotificationsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationsDTO> getNotificationsByTaskId(Long taskId) {
        return notificationRepo.findByTaskId(taskId)
                .stream()
                .map(NotificationsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteNotifications(Long id) {
        notificationRepo.deleteById(id);
    }

    @Override
    public NotificationsDTO markAsRead(Long notificationId) {
        Notifications notif = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notif.setRead(true);
        notificationRepo.save(notif);

        return NotificationsMapper.toDTO(notif);
    }

}
