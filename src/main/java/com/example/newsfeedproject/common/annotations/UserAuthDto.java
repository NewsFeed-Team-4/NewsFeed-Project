package com.example.newsfeedproject.common.annotations;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuthDto {
    private Long userId;
    private String userName;
    private String email;

    @Builder
    public UserAuthDto(Long userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
}
