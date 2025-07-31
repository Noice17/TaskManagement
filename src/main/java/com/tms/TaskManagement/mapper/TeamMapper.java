package com.tms.TaskManagement.mapper;

import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.entity.Team;

public class TeamMapper {

    public static Team toEntity(TeamDTO dto){
        Team team = new Team();
        team.setName(dto.getName());
        team.setImageUrl(dto.getImageUrl());
        return team;
    }

    public static TeamDTO toDTO(Team team){
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setImageUrl(team.getImageUrl());
        return dto;
    }
}
