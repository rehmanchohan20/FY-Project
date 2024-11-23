package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.RoleEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.service.RoleSelection;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleSelectionServiceImpl implements RoleSelection {
    @Autowired
    UserRepository userRepository;



    @Transactional
    @Override
    public User updateUserRole(Long userId, RoleEnum newRole) {
        // Fetch the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        if (newRole == RoleEnum.STUDENT) {
            // Check if a Student entity already exists
            if (user.getStudent() == null) {
                Student student = new Student();
                student.setUser(user);
                student.setCreatedBy(UserCreatedBy.Self);
                user.setStudent(student);
            }
            user.setTeacher(null); // Remove Teacher if changing to Student
        } else if (newRole == RoleEnum.TEACHER) {
            // Check if a Teacher entity already exists
            if (user.getTeacher() == null) {
                Teacher teacher = new Teacher();
                teacher.setUser(user);
                teacher.setCreatedBy(UserCreatedBy.Self);
                user.setTeacher(teacher);
            }
            user.setStudent(null); // Remove Student if changing to Teacher
        } else if (newRole == RoleEnum.GUEST) {
            // Remove both Student and Teacher associations if switching to Guest
            user.setStudent(null);
            user.setTeacher(null);
        }

        // Save the updated user entity
        return userRepository.save(user);
    }
}
