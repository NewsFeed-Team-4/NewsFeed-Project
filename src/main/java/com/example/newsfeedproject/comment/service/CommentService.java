package com.example.newsfeedproject.comment.service;

import com.example.newsfeedproject.comment.entity.Comment;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.comment.dto.CommentRequestDto;
import com.example.newsfeedproject.comment.dto.CommentResponseDto;
import com.example.newsfeedproject.comment.repository.CommentRepository;
import com.example.newsfeedproject.user.repository.UserRepository;
import com.example.newsfeedproject.article.repository.*; //추후 추가
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));
        Comment parent = requestDto.getParentId() != null ?
                commentRepository.findById(requestDto.getParentId()).orElse(null) : null;

        Comment comment = Comment.builder()
                .parent(parent)
                .user(user)
                .article(article)
                .userName(user.getUsername())
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 특정 게시글의 댓글 목록 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.updateContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        commentRepository.delete(comment);
    }
}

