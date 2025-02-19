package com.example.newsfeedproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    ErrorCode error;
    public ApplicationException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public HttpStatus getStatus(){
        return error.getStatus();
    }

    public String getMessage(){
        return error.getMessage();
    }

    public String getCode() {
        return error.name();
    }
}
