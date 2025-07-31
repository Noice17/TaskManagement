package com.tms.TaskManagement.service;

import com.tms.TaskManagement.dto.NotificationsDTO;
import com.tms.TaskManagement.entity.Notifications;

import java.util.List;

public interface NotificationsService {
    void recordNotifications(NotificationsDTO notificationsDTO);

    List<NotificationsDTO> getAllNotifications();
    List<NotificationsDTO> getNotificationsByUserId(Long userId);
    List<NotificationsDTO> getNotificationsByTeamId(Long teamId);
    List<NotificationsDTO> getNotificationsByTaskId(Long taskId);
    void deleteNotifications(Long id);
    NotificationsDTO markAsRead(Long notificationId);

}
