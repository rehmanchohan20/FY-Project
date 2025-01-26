package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(ErrorEnum errorEnum) {
        super(errorEnum.getCode());
    }
}
