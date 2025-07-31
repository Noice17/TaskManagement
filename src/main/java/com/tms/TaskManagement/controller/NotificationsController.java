package com.tms.TaskManagement.controller;

import com.tms.TaskManagement.dto.NotificationsDTO;
import com.tms.TaskManagement.service.NotificationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationService;

    @Autowired
    public NotificationsController(NotificationsService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Void> recordNotification(@Valid @RequestBody NotificationsDTO notificationsDTO) {
        notificationService.recordNotifications(notificationsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationsDTO>> getAllNotifications() {
        List<NotificationsDTO> notifs = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationsDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationsDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<NotificationsDTO>> getNotificationsByTeamId(@PathVariable Long teamId) {
        List<NotificationsDTO> notifications = notificationService.getNotificationsByTeamId(teamId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<NotificationsDTO>> getNotificationsByTaskId(@PathVariable Long taskId) {
        List<NotificationsDTO> notifications = notificationService.getNotificationsByTaskId(taskId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationsDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotifications(id);
        return ResponseEntity.noContent().build();
    }
}
