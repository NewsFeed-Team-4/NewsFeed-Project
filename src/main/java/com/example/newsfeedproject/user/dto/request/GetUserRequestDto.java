package com.example.newsfeedproject.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserRequestDto {
    private final String email;
    private final String password;
}
