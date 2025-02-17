package com.example.newsfeedproject.user.dto.response;

import com.example.newsfeedproject.user.entity.User;
import lombok.Getter;

@Getter
public class CreateUserResponseDto {
    private final String email;
    private final String username;

    public CreateUserResponseDto(User savedUser) {
        this.email = savedUser.getEmail();
        this.username = savedUser.getUsername();
    }
}
