package com.sarfaraz.elearning.exceptions;

import com.sarfaraz.elearning.constants.ErrorEnum;

public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(ErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getDescription());
    }
}
