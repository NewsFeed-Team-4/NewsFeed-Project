package com.example.newsfeedproject.user.dto.response;

import lombok.Getter;

@Getter
public class CreateUserResponseDto {
    private final String email;
    private final String username;

    private CreateUserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public static CreateUserResponseDto of(String email, String username) {
        return new CreateUserResponseDto(email, username);
    }
}
