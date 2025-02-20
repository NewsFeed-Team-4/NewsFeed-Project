package com.example.newsfeedproject.article.dto;

import com.example.newsfeedproject.article.entity.Article;
import lombok.Getter;

@Getter
public class ArticleResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    private final String userName;

    private final String email;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.userName = article.getUserName();
        this.email = article.getEmail();
    }
}
