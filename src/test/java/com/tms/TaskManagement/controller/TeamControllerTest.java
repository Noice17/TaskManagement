package com.tms.TaskManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.TaskManagement.dto.TeamDTO;
import com.tms.TaskManagement.service.TeamService;
import com.tms.TaskManagement.util.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeamService teamService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private TeamDTO teamDTO;

    @BeforeEach
    void setup(){
        teamDTO = new TeamDTO();
        teamDTO.setId(1L);
        teamDTO.setName("Team Sample");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createTeam_success() throws Exception {
        TeamDTO teamPayload = new TeamDTO();
        teamPayload.setName("Team Sample");

        MockMultipartFile teamPart = new MockMultipartFile(
                "team", "", "application/json",
                objectMapper.writeValueAsBytes(teamPayload)
        );


        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "team.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-data".getBytes()
        );

        when(teamService.createTeam(any(TeamDTO.class))).thenReturn(teamDTO);

        mockMvc.perform(multipart("/api/teams")
                        .file(teamPart)
                        .file(imagePart)
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team Sample"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getTeamById_success() throws Exception{
        when(teamService.getTeamById(1L)).thenReturn(Optional.ofNullable(teamDTO));

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team Sample"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateTeam_success() throws  Exception{
        when(teamService.updateTeam(eq(1L), any(TeamDTO.class))).thenReturn(teamDTO);

        MockMultipartFile teamPart = new MockMultipartFile(
                "team", "", "application/json", objectMapper.writeValueAsBytes(teamDTO)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "team.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-value".getBytes()
        );

        mockMvc.perform(multipart("/api/teams/1")
                        .file(teamPart)
                        .file(imagePart)
                        .with(csrf())
                        .with(putMultipart()) // <- Force multipart to use PUT
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team Sample"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllTeams_success() throws Exception{
        when(teamService.getAllTeams()).thenReturn(List.of(teamDTO));

        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Team Sample"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteTeam_success() throws  Exception{
        mockMvc.perform(delete("/api/teams/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    private static RequestPostProcessor putMultipart() {
        return request -> {
            request.setMethod("PUT");
            return request;
        };
    }
}
