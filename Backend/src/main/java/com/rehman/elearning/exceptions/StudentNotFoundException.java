package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(ErrorEnum errorEnum) {
        super(errorEnum.getCode());
    }
}
