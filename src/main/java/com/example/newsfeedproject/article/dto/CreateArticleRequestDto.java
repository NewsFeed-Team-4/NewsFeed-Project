package com.example.newsfeedproject.article.dto;

import lombok.Getter;

@Getter
public class CreateArticleRequestDto {

    private final String title;

    private final String content;

    private final String userName;

    private final String email;

    public CreateArticleRequestDto(String title, String content, String userName, String email) {
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.email = email;
    }
}
