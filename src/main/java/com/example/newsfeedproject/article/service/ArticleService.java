package com.example.newsfeedproject.article.service;


import com.example.newsfeedproject.article.dto.ArticleResponseDto;
import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.article.repository.ArticleRepository;
import com.example.newsfeedproject.recommand.entity.RecommendArticle;
import com.example.newsfeedproject.recommand.repository.RecommendArticleRepository;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RecommendArticleRepository recommendArticleRepository;
    private final UserRepository userRepository;

    //게시글 CRUD

    public ArticleResponseDto save(String title, String contents, String userName, String email) {
        User findUser = userRepository.findByEmailOrElseThrow(email);

        Article article = Article.builder()
                .user(findUser)
                .userName(userName)
                .title(title)
                .content(contents)
                .email(email)
                .build();

        Article savedArticle = articleRepository.save(article);

        return new ArticleResponseDto(savedArticle);
    }

    //게시글 좋아요
    @Transactional
    public void addRecommendArticle(Long articleId, Long userId) {
        //좋아요한 게시글과 사용자가 존재하는지 검증
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        if(article.getUser().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 게시물에는 좋아요를 남길 수 없습니다.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        //중복데이터 체크
        recommendArticleRepository.findByArticleIdAndUserId(articleId, userId).ifPresent(entity -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 추천한 게시글입니다");
        });

        RecommendArticle recommendArticle = RecommendArticle.builder()
                .article(article)
                .user(user)
                .build();

        recommendArticleRepository.save(recommendArticle);
        article.incrementRecommendCount();
    }

    @Transactional
    public void cancelRecommendArticle(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        Long deleted = recommendArticleRepository.deleteRecommendArticleByArticleIdAndUserId(articleId, userId);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "추천한 기록이 없습니다.");
        }
        article.decrementRecommendCount();
    }
}
