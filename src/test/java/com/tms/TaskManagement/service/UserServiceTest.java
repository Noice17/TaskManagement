package com.tms.TaskManagement.service;

import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.dto.UserUpdateDTO;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.exception.custom.EmailAlreadyExistsException;
import com.tms.TaskManagement.exception.custom.UserNotFoundException;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.implementation.UserServiceImpl;
import com.tms.TaskManagement.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password123");
        userDTO.setUsername("testUser");
        userDTO.setTeamId(1L);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testUser");
    }

    @Test
    void createUser_successful() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("hashedPassword");
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);
        when(messageUtil.getMessage(anyString(), any())).thenReturn("Email already exists");

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void updateUser_successful() {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setUsername("newName");
        updateDTO.setEmail("new@example.com");
        updateDTO.setPassword("newPass123");
        updateDTO.setTeamId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));
        when(passwordEncoder.encode("newPass123")).thenReturn("hashedNewPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updated = userService.updateUser(1L, updateDTO);

        assertNotNull(updated);
        assertEquals("new@example.com", updated.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage(anyString(), any())).thenReturn("User not found");

        UserUpdateDTO updateDTO = new UserUpdateDTO();

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(99L, updateDTO));
    }

    @Test
    void getUserById_found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<UserDTO> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void getAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDTO> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void deleteUser_notFound_throwsException() {
        when(userRepository.existsById(999L)).thenReturn(false);
        when(messageUtil.getMessage(anyString(), any())).thenReturn("User not found");

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(999L));
    }

    @Test
    void emailExists_returnsTrue() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(userService.emailExists("test@example.com"));
    }

    @Test
    void getUserByEmail_found() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Optional<UserDTO> result = userService.getUserByEmail("test@example.com");
        assertTrue(result.isPresent());
    }
}
