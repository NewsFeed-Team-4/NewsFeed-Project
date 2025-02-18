package com.example.newsfeedproject.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserPasswordRequestDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
