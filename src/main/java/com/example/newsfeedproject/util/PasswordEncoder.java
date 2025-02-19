package com.example.newsfeedproject.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;

public class PasswordEncoder {
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    private boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }

    public void verify(String rawPassword, String encodedPassword) {

        if (!matches(rawPassword, encodedPassword)) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }

    }
}
