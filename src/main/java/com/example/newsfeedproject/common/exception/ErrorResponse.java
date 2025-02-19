package com.example.newsfeedproject.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ErrorResponse {

    private String code;
    private String message;

    @Setter
    private Map<String, String> fieldError;

    @Builder
    public ErrorResponse(String code, String message) {
        this.message = message;
        this.code = code;
    }
}
