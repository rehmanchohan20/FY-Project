package com.sarfaraz.elearning.rest.dto.inbound;

import com.sarfaraz.elearning.constants.UserCreatedBy;


public class UserRequestDTO {
    private String username;
    private String email;
    private String password;


    // private UserCreatedBy createdBy; // New field


    // Getters and Setters
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

//    public UserCreatedBy  getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(UserCreatedBy createdBy) {
//        this.createdBy = createdBy;
//    }
}
