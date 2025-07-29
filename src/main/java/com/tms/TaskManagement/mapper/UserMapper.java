package com.tms.TaskManagement.mapper;

import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.entity.User;

public class UserMapper {

    public static User toEntity(UserDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return  user;
    }

    public static User toEntity(UserDTO dto, Team team){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setTeam(team);
        return user;
    }

    public static UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        if (user.getTeam() != null) {
            dto.setTeamId(user.getTeam().getId());
            dto.setTeamName(user.getTeam().getName());
        }
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

}
