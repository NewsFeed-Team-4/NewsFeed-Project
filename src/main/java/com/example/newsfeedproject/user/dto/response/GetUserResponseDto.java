package com.example.newsfeedproject.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetUserResponseDto {
    private final String username;
    private final String image;
    private String description;
    private final Long articleCount;
    private final Long friendsCount;
    private final List<Article> articleList;

    @Getter
    @RequiredArgsConstructor
    private class Article {
        private final Long id;
        private final String image;
    }
}
