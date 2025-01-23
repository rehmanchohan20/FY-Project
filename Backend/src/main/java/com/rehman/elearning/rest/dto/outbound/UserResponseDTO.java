package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.model.User;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean isTeacher;
    private Boolean isAdmin;
    private String image;



    public UserResponseDTO() {
    }

    // Constructor that takes a User object
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.isTeacher = user.isTeacher(); // Default to Teacher if no role is found
        this.isAdmin = user.getAdmin(); // Default to Admin if no role is found
        this.image = user.getImage();
    }

    public UserResponseDTO(Long id, String username, String email, String image, Boolean isTeacher, Boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.image = image;
        this.isTeacher = isTeacher;
        this.isAdmin = isAdmin;
    }

    public UserResponseDTO(Long id, String username, String email) {
        this.id=id;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
