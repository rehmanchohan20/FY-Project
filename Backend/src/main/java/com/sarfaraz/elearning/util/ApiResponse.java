package com.sarfaraz.elearning.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarfaraz.elearning.constants.GeneralErrorEnum;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String code;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        super();
        this.code = GeneralErrorEnum.SUCCESS.getCode();
        this.data = data;
    }

    public ApiResponse(String code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
