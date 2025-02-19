package com.example.newsfeedproject.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateArticleRequestDto {

    @NotBlank(message = "제목을 입력 해주세요.")
    private final String title;

    private final String content;

    public CreateArticleRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
