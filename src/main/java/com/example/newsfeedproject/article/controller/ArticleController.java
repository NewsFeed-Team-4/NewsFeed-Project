package com.example.newsfeedproject.article.controller;

import com.example.newsfeedproject.article.dto.ArticleResponseDto;
import com.example.newsfeedproject.article.dto.CreateArticleRequestDto;
import com.example.newsfeedproject.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    //게시글 CRUD

    @PostMapping
    public ResponseEntity<ArticleResponseDto> save(@RequestBody CreateArticleRequestDto requestDto) {

        ArticleResponseDto articleResponseDto = articleService.save(requestDto.getTitle(), requestDto.getContent(), requestDto.getUserName(), requestDto.getEmail());

        return new ResponseEntity<>(articleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ArticleResponseDto> articleResponseDtoList = articleService.findAll(page, size);

        return new ResponseEntity<>(articleResponseDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @SessionAttribute(name = "email") String email) {

        articleService.delete(id, email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

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
