package com.example.newsfeedproject.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long parentId; // 대댓글인 경우 작성자 댓글 ID
    private Long articleId; // 댓글이 달린 게시글 ID
    private String content; // 댓글 내용

    public CommentRequestDto(Long parentId, Long articleId, String content) {
        this.parentId = parentId;
        this.articleId = articleId;
        this.content = content;
    }

}
