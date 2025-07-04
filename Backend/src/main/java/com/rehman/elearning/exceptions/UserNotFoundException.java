package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(ErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getDescription());
    }
}
