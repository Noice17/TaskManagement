package com.tms.TaskManagement.mapper;

import com.tms.TaskManagement.dto.NotificationsDTO;
import com.tms.TaskManagement.entity.Notifications;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;

public class NotificationsMapper {

    public static NotificationsDTO toDTO(Notifications notification) {
        if (notification == null) return null;

        return new NotificationsDTO(
                notification.getId(),
                notification.getUser().getId(),
                notification.getTeam().getId(),
                notification.getTask().getId(),
                notification.getDescription(),
                notification.getCreatedAt(),
                notification.isRead()
        );
    }

    public static Notifications toEntity(NotificationsDTO dto, User user, Team team, Task task) {
        if (dto == null) return null;

        Notifications notification = new Notifications();
        notification.setId(dto.getId());
        notification.setUser(user);
        notification.setTeam(team);
        notification.setTask(task);
        notification.setDescription(dto.getDescription());
        notification.setRead(dto.isRead());
        notification.setCreatedAt(dto.getCreatedAt());

        return notification;
    }
}
