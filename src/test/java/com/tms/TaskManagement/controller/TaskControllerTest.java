package com.tms.TaskManagement.controller;

import com.tms.TaskManagement.dto.TaskDTO;
import com.tms.TaskManagement.entity.Task;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.repository.TaskRepository;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.implementation.TaskServiceImpl;
import com.tms.TaskManagement.util.MessageUtil;
import com.tms.TaskManagement.util.NotificationsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private MessageUtil messageUtil;
    @Mock private NotificationsUtil notificationsUtil;

    @InjectMocks private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks_TC001() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(taskService.getAllTasks().isEmpty());
    }

    @Test
    void testGetTaskByIdValid_TC002() {
        Task task = new Task();
        task.setId(1L);
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setDueDate(java.time.LocalDateTime.now().plusDays(1));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<TaskDTO> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testGetTaskByIdInvalid_TC003() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<TaskDTO> result = taskService.getTaskById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateTaskValid_TC004() {
        TaskDTO dto = new TaskDTO();
        dto.setUserId(1L);

        User user = new User();
        Team team = new Team();
        team.setId(2L);
        user.setTeam(team);

        Task task = new Task();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(taskRepository.save(any())).thenReturn(task);

        List<TaskDTO> result = taskService.createTask(dto, null);
        assertNotNull(result);
        assertEquals(1, result.size());
    }


    @Test
    void testCreateTaskMissingTitle_TC005() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setUserId(1L);

        Team team = new Team();
        team.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setTeam(team);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTaskName(null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        List<TaskDTO> result = taskService.createTask(taskDTO, null);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNull(result.get(0).getTaskName());
    }



    @Test
    void testUpdateTask_TC006() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setUserId(1L);
        taskDTO.setTaskName("Updated Task");

        // Mock User and Team
        Team mockTeam = new Team();
        mockTeam.setId(1L);

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setTeam(mockTeam);

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setUser(mockUser);
        existingTask.setTaskName("Old Task");

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setUser(mockUser);
        updatedTask.setTaskName("Updated Task");

        // Mock repository behavior
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Act
        TaskDTO result = taskService.updateTask(taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Task", result.getTaskName());
    }


    @Test
    void testUpdateTaskNonExistent_TC007() {
        TaskDTO dto = new TaskDTO();
        dto.setId(999L);
        dto.setUserId(1L);

        when(taskRepository.existsById(999L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> taskService.updateTask(dto));
    }

    @Test
    void testDeleteTask_TC008() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDeleteTaskInvalid_TC009() {
        when(taskRepository.existsById(999L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(999L));
    }

    @Test
    void testCreateTaskWithLongTitle_TC010() {
        TaskDTO dto = new TaskDTO();
        dto.setUserId(1L);
        dto.setTaskName("a".repeat(500));

        User user = new User();
        Team team = new Team();
        team.setId(2L);
        user.setTeam(team);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(team));
        when(taskRepository.save(any())).thenThrow(new RuntimeException("Too long"));

        assertThrows(RuntimeException.class, () -> taskService.createTask(dto, null));
    }


    @Test
    void testGetAllTasksAfterCreate_TC011() {
        when(taskRepository.findAll()).thenReturn(List.of(new Task(), new Task()));
        List<TaskDTO> result = taskService.getAllTasks();
        assertEquals(2, result.size());
    }
}
