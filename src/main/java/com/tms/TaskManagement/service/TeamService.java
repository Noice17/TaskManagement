package com.tms.TaskManagement.service;

import com.tms.TaskManagement.dto.TeamDTO;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    TeamDTO createTeam(TeamDTO teamDTO);
    TeamDTO updateTeam(Long id, TeamDTO teamDTO);
    Optional<TeamDTO> getTeamById(Long id);
    List<TeamDTO> getAllTeams();
    void deleteTeam(Long id);

    Optional<TeamDTO> getTeamByName(String name);
    boolean teamNameExists(String name);
}
