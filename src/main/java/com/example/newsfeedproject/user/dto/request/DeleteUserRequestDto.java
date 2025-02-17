package com.example.newsfeedproject.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeleteUserRequestDto {
    private final String email;
    private final String password;
}
