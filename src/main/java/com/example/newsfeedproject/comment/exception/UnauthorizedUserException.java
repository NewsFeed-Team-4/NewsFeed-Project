package com.example.newsfeedproject.comment.exception;

import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;

public class UnauthorizedUserException extends ApplicationException {
    public UnauthorizedUserException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
