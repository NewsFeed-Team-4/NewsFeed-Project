package com.example.newsfeedproject.comment.service;

import com.example.newsfeedproject.article.repository.ArticleRepository;
import com.example.newsfeedproject.comment.entity.Comment;
import com.example.newsfeedproject.comment.exception.UnauthorizedUserException;
import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.comment.dto.CommentRequestDto;
import com.example.newsfeedproject.comment.dto.CommentResponseDto;
import com.example.newsfeedproject.comment.repository.CommentRepository;
import com.example.newsfeedproject.user.repository.UserRepository;
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
    public CommentResponseDto createComment(Long articleId, CommentRequestDto requestDto, Long userId) {
        // 사용자 없음 예외처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        //  게시글 없음 예외 처리
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));
        //  메인  댓글  없음 예외처리
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
                .map(CommentResponseDto::new) // Comment -> CommentResponseDto 변환
                .collect(Collectors.toList());
    }


    // 댓글 수정

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, Long userId) {
        Comment comment = getCommentById(commentId); // 중복 제거된 메서드 사용
        validatePermission(comment, userId); // 권한 체크 메서드 사용

        comment.updateContent(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = getCommentById(commentId); // 중복 제거된 메서드 사용
        validatePermission(comment, userId); // 권한 체크 메서드 사용

        commentRepository.delete(comment);
    }

    // 중복 제거: 댓글 조회 메서드
    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));
    }

    // 중복 제거: 댓글 수정/삭제 권한 체크 메서드
    private void validatePermission(Comment comment, Long userId) {
        User articleOwner = comment.getArticle().getUser(); // 게시글 작성자

        if (!comment.getUser().getId().equals(userId) && !articleOwner.getId().equals(userId)) {
            throw new UnauthorizedUserException(); // 권한 없음 예외
        }
    }

}



