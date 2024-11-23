package com.rehman.elearning.exceptions;

import com.rehman.elearning.constants.ErrorEnum;

public class AccountNotRegisterException extends ApplicationException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AccountNotRegisterException(ErrorEnum errorEnum) {
        super(errorEnum.getCode(), errorEnum.getDescription());
    }
}
