package com.example.newsfeedproject.comment.service;

import com.example.newsfeedproject.comment.entity.Comment;
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
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        // 사용자 없음 예외처리
        User user = userRepository.findById(requestDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        //  게시글 없음 예외 처리
        Article article = articleRepository.findById(requestDto.getArticleId())
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
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found")); // 댓글 없음 예외

        // 댓글 작성자와 수정 요청자가 같은지 확인
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedUserException("You are not authorized to update this comment"); // 권한 없음 예외
        }

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

