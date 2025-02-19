package com.example.newsfeedproject.comment.controller;


import com.example.newsfeedproject.comment.dto.CommentRequestDto;
import com.example.newsfeedproject.comment.dto.CommentResponseDto;
import com.example.newsfeedproject.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 게시글의 댓글 목록 조회
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentResponseDto> responseDtos = commentService.getCommentsByArticle(articleId);
        return ResponseEntity.ok(responseDtos);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
