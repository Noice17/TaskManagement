package com.tms.TaskManagement.controller;

import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //Create
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO){
        TeamDTO created = teamService.createTeam(teamDTO);
        return ResponseEntity.ok(created);
    }

    //Read
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id){
        return teamService.getTeamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams(){
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    //Update
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamDTO teamDTO){
        try{
            TeamDTO updated = teamService.updateTeam(id, teamDTO);
            return ResponseEntity.ok(updated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Delete
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        try{
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
