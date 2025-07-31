package com.tms.TaskManagement.util;


import com.tms.TaskManagement.dto.NotificationsDTO;
import com.tms.TaskManagement.entity.Notifications;
import com.tms.TaskManagement.service.NotificationsService;
import org.springframework.stereotype.Component;

@Component
public class NotificationsUtil {

    private final NotificationsService notificationsService;

    public NotificationsUtil(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    public void notif(Long userId, Long teamId, Long taskId, String description, boolean read) {
        NotificationsDTO notificationsDTO = new NotificationsDTO();
        notificationsDTO.setUserId(userId);
        notificationsDTO.setTeamId(teamId);
        notificationsDTO.setTaskId(taskId);
        notificationsDTO.setDescription(description);
        notificationsDTO.setRead(read);
        notificationsService.recordNotifications(notificationsDTO);
    }
}
