package com.example.newsfeedproject.comment.dto;


import com.example.newsfeedproject.comment.entity.Comment;
import lombok.Getter;


@Getter
public class CommentResponseDto {
    private Long id;
    private Long parentId;
    private Long userId;
    private String userName;
    private String content;



    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.userId = comment.getUser().getId();
        this.userName = comment.getUserName();
        this.content = comment.getContent();
    }
}