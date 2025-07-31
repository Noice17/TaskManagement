package com.tms.TaskManagement.dto;


import jakarta.validation.constraints.NotBlank;

public class TeamDTO {
    private Long id;

    @NotBlank(message = "Team name is required")
    private String name;

    private String imageUrl;

    public TeamDTO() {
    }

    public TeamDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamDTO(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
