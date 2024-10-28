package com.sarfaraz.elearning.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorApi {
    public record Response(String code, String details, long timestamp, List<String> errors){}
}