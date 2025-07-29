package com.tms.TaskManagement.service.implementation;

import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.dto.UserUpdateDTO;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.exception.custom.EmailAlreadyExistsException;
import com.tms.TaskManagement.exception.custom.TeamNotFoundException;
import com.tms.TaskManagement.exception.custom.UserNotFoundException;
import com.tms.TaskManagement.mapper.UserMapper;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.repository.UserRepository;
import com.tms.TaskManagement.service.UserService;
import com.tms.TaskManagement.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;
    private final TeamRepository teamRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MessageUtil messageUtil, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
        this.teamRepository = teamRepository;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException(messageUtil.getMessage("error.email.exists", userDTO.getEmail()));
        }

        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if(userDTO.getTeamId() != null){
            Team team = teamRepository.findById(userDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException(
                            messageUtil.getMessage("team.not_found", userDTO.getTeamId())
                    ));
            user.setTeam(team);
        }

        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(messageUtil.getMessage("error.user.not_found", id))
                );
        if(userDTO.getUsername() != null) user.setUsername(userDTO.getUsername());
        if(userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if(userDTO.getPassword() != null && !userDTO.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if(userDTO.getTeamId() != null){
            Team team = teamRepository.findById(userDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException(
                            messageUtil.getMessage("team.not_found", userDTO.getTeamId())
                    ));
            user.setTeam(team);
        }

        if(userDTO.getRole() != null) user.setRole(userDTO.getRole());

        User updated = userRepository.save(user);
        return UserMapper.toDTO(updated);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(messageUtil.getMessage("error.user.not_found",id));
        }
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
