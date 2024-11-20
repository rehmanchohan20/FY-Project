package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(ErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getDescription());
    }
}
