package com.example.newsfeedproject.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserInfoRequestDto {
    private String email;
    private String username;
    private String password;
    private String description;
    private String imageUrl;
}
