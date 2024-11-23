package com.rehman.elearning.rest.dto.inbound;

import com.rehman.elearning.constants.RoleEnum;

public class RoleRequestDTO {

    private RoleEnum role;

    // Getter and setter for role
    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
