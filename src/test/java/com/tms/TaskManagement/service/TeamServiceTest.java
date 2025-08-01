package com.tms.TaskManagement.service;

import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.entity.Team;
import com.tms.TaskManagement.exception.custom.TeamAlreadyExistsException;
import com.tms.TaskManagement.repository.TeamRepository;
import com.tms.TaskManagement.service.implementation.TeamServiceImpl;
import com.tms.TaskManagement.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private TeamServiceImpl teamService;

    private TeamDTO teamDTO;
    private Team team;

    @BeforeEach
    void setUp(){
        teamDTO = new TeamDTO();
        teamDTO.setName("Team Sample");

        team = new Team();
        team.setId(1L);
        team.setName("Team Sample");
    }

    @Test
    void createTeam_successful(){
        when(teamRepository.existsByName(teamDTO.getName())).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenAnswer(invocation -> {
            Team t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        TeamDTO result = teamService.createTeam(teamDTO);

        assertNotNull(result);
        assertEquals("Team Sample", result.getName());
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    void createTeam_nameAlreadyExists_throwsException(){
        when(teamRepository.existsByName(teamDTO.getName())).thenReturn(true);
        when(messageUtil.getMessage(anyString(), any())).thenReturn("Team name already exists");
        assertThrows(TeamAlreadyExistsException.class, () -> teamService.createTeam(teamDTO));
    }

    @Test
    void updateTeam_successful(){
        TeamDTO updateDTO = new TeamDTO();
        updateDTO.setName("newName");

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        TeamDTO updated = teamService.updateTeam(1L, updateDTO);

        assertNotNull(updated);
        assertEquals("newName", updated.getName());
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    void getTeamById_found(){
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        Optional<TeamDTO> result = teamService.getTeamById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void getAllUsers_returnsList(){
        when(teamRepository.findAll()).thenReturn(List.of(team));
        List<TeamDTO> teams = teamService.getAllTeams();
        assertEquals(1, teams.size());
    }

    @Test
    void teamNameExists_returnsTrue(){
        when(teamRepository.existsByName("Team Sample")).thenReturn(true);
        assertTrue(teamService.teamNameExists("Team Sample"));
    }

    @Test
    void getTeamByName_found(){
        when(teamRepository.findByName("Team Sample")).thenReturn(Optional.of(team));
        Optional<TeamDTO> result = teamService.getTeamByName("Team Sample");
        assertTrue(result.isPresent());
    }
}
