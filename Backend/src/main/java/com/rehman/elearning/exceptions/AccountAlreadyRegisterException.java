package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class AccountAlreadyRegisterException extends ApplicationException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AccountAlreadyRegisterException(ErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getDescription());
    }
}
