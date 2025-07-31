package com.tms.TaskManagement.repository;


import com.tms.TaskManagement.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Notification;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
//    List<Notifications> getAllNotifications();
    List<Notifications> findByUserId(Long userId);
    List<Notifications> findByTeamId(Long teamId);
    List<Notifications> findByTaskId(Long taskId);
}
