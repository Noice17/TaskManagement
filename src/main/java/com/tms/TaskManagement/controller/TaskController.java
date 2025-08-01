package com.tms.TaskManagement.controller;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        taskDTO.setId(id);
        TaskDTO updatedTask = taskService.updateTask(taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam Task.TaskStatus status) {
        TaskDTO updated = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatusByUserId(
            @PathVariable Long userId,
            @PathVariable String status
    ) {
        List<TaskDTO> tasks = taskService.getTasksByStatusAndUserId(status.toUpperCase(), userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Void> markTaskAsDone(@PathVariable Long taskId) {
        taskService.markTaskAsDone(taskId);
        return ResponseEntity.ok().build();
    }

    // ✅ New endpoint: get overdue tasks
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskDTO>> getOverdueTasks() {
        List<TaskDTO> tasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/users/{userId}/personal")
    public ResponseEntity<List<TaskDTO>> getPersonalTasks(@PathVariable Long userId) {
        List<TaskDTO> tasks = taskService.getPersonalTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/users/{userId}/personal")
    public ResponseEntity<TaskDTO> createPersonalTask(
            @PathVariable Long userId,
            @RequestBody TaskDTO taskDTO) {

        // Validate task name
        if (taskDTO.getTaskName() == null || taskDTO.getTaskName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        TaskDTO createdTask = taskService.createPersonalTask(userId, taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }




}
