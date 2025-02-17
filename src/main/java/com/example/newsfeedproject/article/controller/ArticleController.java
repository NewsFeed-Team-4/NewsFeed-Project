package com.example.newsfeedproject.article.controller;

import com.example.newsfeedproject.article.service.ArticleService;
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
    public ResponseEntity<Void> addRecommendArticle(@PathVariable Long articleId, @SessionAttribute(name = "userId") Long userId) {

        articleService.addRecommendArticle(articleId, userId);

        return ResponseEntity.ok().build();
    }

    //게시글 좋아요 취소
    @DeleteMapping("/{articleId}/favorite")
    public ResponseEntity<Void> removeRecommendArticle(@PathVariable Long articleId, @SessionAttribute(name = "userId") Long userId) {

        articleService.cancelRecommendArticle(articleId, userId);

        return ResponseEntity.ok().build();
    }
}
