package com.tms.TaskManagement.controller;

import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private static final String UPLOAD_DIR = "uploads/team_cover/";

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    //Create
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDTO> createTeam(
            @Valid @RequestPart("team") TeamDTO teamDTO,
            @RequestPart(value = "image", required = false)MultipartFile image
            )throws IOException{

        if(image !=null && !image.isEmpty()){
            teamDTO.setImageUrl(saveImage(image));
        }

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
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long id,
            @Valid @RequestPart("team") TeamDTO teamDTO,
            @RequestPart(value = "image", required = false) MultipartFile image) throws  IOException{

        if (image != null && !image.isEmpty()){
            teamDTO.setImageUrl(saveImage(image));
        }

        try{
            TeamDTO updated = teamService.updateTeam(id, teamDTO);
            return ResponseEntity.ok(updated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        try{
            teamService.deleteTeam(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Helper
    private String saveImage(MultipartFile file) throws  IOException{
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(),
                uploadPath.resolve(filename),
                StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/team_cover/"  + filename;
    }
}
