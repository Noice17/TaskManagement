package com.tms.TaskManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.TaskManagement.dto.UserDTO;
import com.tms.TaskManagement.dto.UserUpdateDTO;
import com.tms.TaskManagement.entity.User;
import com.tms.TaskManagement.service.UserService;
import com.tms.TaskManagement.util.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private UserUpdateDTO updateDTO;




    @BeforeEach
    void setup() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setUsername("testUser");
        userDTO.setRole(User.UserRole.USER);

        updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("new@example.com");
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createUser_success() throws Exception {
        UserDTO userPayload = new UserDTO();
        userPayload.setEmail("test@example.com");
        userPayload.setUsername("testUser");
        userPayload.setPassword("pass123");
        userPayload.setRole(User.UserRole.USER);

        MockMultipartFile userPart = new MockMultipartFile(
                "user", "", "application/json",
                objectMapper.writeValueAsBytes(userPayload)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-data".getBytes()
        );

        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(multipart("/api/users")
                        .file(userPart)
                        .file(imagePart)
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById_success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.ofNullable(userDTO));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser_success() throws Exception {
        when(userService.updateUser(eq(1L), any(UserUpdateDTO.class))).thenReturn(userDTO);

        MockMultipartFile userPart = new MockMultipartFile(
                "user", "", "application/json",
                objectMapper.writeValueAsBytes(updateDTO)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "avatar.jpg", MediaType.IMAGE_JPEG_VALUE, "fake-image-data".getBytes()
        );

        mockMvc.perform(multipart("/api/users/1")
                        .file(userPart)
                        .file(imagePart)
                        .with(csrf())
                        .with(putMultipart()) // <- Force multipart to use PUT
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }




    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAllUsers_success() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser_success() throws Exception {
        mockMvc.perform(delete("/api/users/1")
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
