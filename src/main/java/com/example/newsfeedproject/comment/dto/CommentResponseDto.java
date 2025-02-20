package com.example.newsfeedproject.comment.dto;


import com.example.newsfeedproject.comment.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id; // 댓글 ID
    private Long parentId; // 작성자 댓글 ID (대댓글인 경우)
    private Long userId; // 작성자 ID
    private String userName; // 작성자 이름
    private String content; // 댓글 내용
    private final LocalDateTime createdAt; // 작성일
    private final LocalDateTime updatedAt; // 수정일



    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getUsername(); // 작성자 이름
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt(); // 작성일
        this.updatedAt = comment.getUpdatedAt(); // 수정일
    }
}