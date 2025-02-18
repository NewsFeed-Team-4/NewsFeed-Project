package com.example.newsfeedproject.user.dto.response;

import com.example.newsfeedproject.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserListResponse {
    private final List<GetUserResponseDto> data;

    public static UserListResponse from(List<User> users) {
        List<GetUserResponseDto> userList = users.stream()
                .map(GetUserResponseDto::new)
                .toList();
        return new UserListResponse(userList);
    }
}
