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
                .map(user -> GetUserResponseDto.builder()
                        .username(user.getUsername())
                        .description(user.getDescription())
                        .imageUrl(user.getImageUrl())
                        .articleList(GetUserResponseDto.UserArticle.ofList(user.getArticles()))
                        .articleCount(user.getArticles().size())
                        .friendsCount(null)//구현필요
                        .build())
                .toList();
        return new UserListResponse(userList);
    }
}
