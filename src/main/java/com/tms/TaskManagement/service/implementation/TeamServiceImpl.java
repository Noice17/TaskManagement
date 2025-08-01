package com.tms.TaskManagement.service.implementation;

import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.exception.custom.TeamAlreadyExistsException;
import com.tms.TaskManagement.exception.custom.TeamNotFoundException;
import com.tms.TaskManagement.mapper.TeamMapper;
import com.tms.TaskManagement.mapper.UserMapper;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.service.TeamService;

import com.tms.TaskManagement.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MessageUtil messageUtil;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, MessageUtil messageUtil) {
        this.teamRepository = teamRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    @Transactional
    public TeamDTO createTeam(TeamDTO teamDTO) {
        if(teamRepository.existsByName(teamDTO.getName())){
            throw new TeamAlreadyExistsException(messageUtil.getMessage("team.name.exists", teamDTO.getName()));
        }

        Team team = TeamMapper.toEntity(teamDTO);
        Team saved = teamRepository.save(team);

        return TeamMapper.toDTO(saved);

    }

    @Override
    @Transactional
    public TeamDTO updateTeam(Long id, TeamDTO teamDTO) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new TeamNotFoundException(messageUtil.getMessage("team.not_found", id))
                        );

        if(teamDTO.getName() != null) team.setName(teamDTO.getName());
        if(teamDTO.getImageUrl() != null) team.setImageUrl(teamDTO.getImageUrl());
        Team updated = teamRepository.save(team);
        return TeamMapper.toDTO(updated);
    }

    @Override
    public Optional<TeamDTO> getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(TeamMapper::toDTO);
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        if(!teamRepository.existsById(id)){
            throw new TeamNotFoundException(messageUtil.getMessage("team.not_found", id));
        }
        teamRepository.deleteById(id);
    }

    @Override
    public Optional<TeamDTO> getTeamByName(String name) {
        return teamRepository.findByName(name)
                .map(TeamMapper::toDTO);
    }

    @Override
    public boolean teamNameExists(String name) {
        return teamRepository.existsByName(name);
    }
}
