package com.example.newsfeedproject.user.dto.response;


import com.example.newsfeedproject.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String name;
    private final String email;
    //필드명 수정 필요
    private final String description;

    public UserResponseDto(User user) {
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.description ="";
    }
}
