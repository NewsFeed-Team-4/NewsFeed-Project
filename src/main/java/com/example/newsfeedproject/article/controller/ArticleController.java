package com.example.newsfeedproject.article.controller;

import com.example.newsfeedproject.article.service.ArticleService;
import com.example.newsfeedproject.common.annotations.UserAuth;
import com.example.newsfeedproject.common.annotations.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    //게시글 CRUD
    //게시글 좋아요

    //게시글 좋아요 등록
    @PostMapping("/{articleId}/favorite")
    public ResponseEntity<Void> addRecommendArticle(@PathVariable Long articleId,
                                                    @UserAuth UserAuthDto userAuthDto) {
        articleService.addRecommendArticle(articleId, userAuthDto.getUserId());

        return ResponseEntity.ok().build();
    }

    //게시글 좋아요 취소
    @DeleteMapping("/{articleId}/favorite")
    public ResponseEntity<Void> removeRecommendArticle(@PathVariable Long articleId,
                                                       @UserAuth UserAuthDto userAuthDto) {
        articleService.cancelRecommendArticle(articleId, userAuthDto.getUserId());

        return ResponseEntity.ok().build();
    }
}
