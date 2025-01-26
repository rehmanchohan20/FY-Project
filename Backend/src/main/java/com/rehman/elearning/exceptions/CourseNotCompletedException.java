package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class CourseNotCompletedException extends RuntimeException {
    public CourseNotCompletedException(ErrorEnum errorEnum) {
        super(errorEnum.getDescription());
    }
}
