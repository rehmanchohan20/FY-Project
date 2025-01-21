package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.model.User;

public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String image;



    public UserResponseDTO() {
    }

    // Constructor that takes a User object
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = String.valueOf(user.isTeacher());  // Default to Teacher if no role is found
        this.image = user.getImage();
    }

    public UserResponseDTO(String fullName) {
        this.fullName = fullName;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


//package com.rehman.elearning.rest.dto.outbound;
//
//public class UserResponseDTO {
//    private Long id;
//    private String username;
//    private String email;
//
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
