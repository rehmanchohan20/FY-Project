package com.rehman.elearning.service;

import com.rehman.elearning.constants.RoleEnum;
import com.rehman.elearning.model.User;

public interface RoleSelection {
    public User updateUserRole(Long userId, RoleEnum newRole);
}
