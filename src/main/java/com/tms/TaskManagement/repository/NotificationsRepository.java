package com.tms.TaskManagement.repository;


import com.tms.TaskManagement.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Notification;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    boolean existsById(Long id);
}
