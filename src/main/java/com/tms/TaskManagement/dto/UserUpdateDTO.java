package com.tms.TaskManagement.dto;

import com.tms.TaskManagement.entity.User;

import java.time.LocalDate;

public class UserUpdateDTO {
    private String username;
    private String email;
    private String password; // Optional
    private User.UserRole role;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String username, String email, String password, User.UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.UserRole getRole() {
        return role;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }
}
