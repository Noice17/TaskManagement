package com.tms.TaskManagement.service;

import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.dto.UserUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserUpdateDTO userDTO);
    Optional<UserDTO> getUserById(Long id);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
    Optional<UserDTO> getUserByEmail(String email);
    boolean emailExists(String email);
}
