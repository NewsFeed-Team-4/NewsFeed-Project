package com.example.newsfeedproject.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {
    private String email;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String description;
    private String imageUrl;
}
