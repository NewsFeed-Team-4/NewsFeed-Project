package com.example.newsfeedproject.article.controller;

import com.example.newsfeedproject.article.dto.ArticleResponseDto;
import com.example.newsfeedproject.article.dto.CreateArticleRequestDto;
import com.example.newsfeedproject.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<ArticleResponseDto>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ArticleResponseDto> articleResponseDtoPage = articleService.findAll(pageable);
        return new ResponseEntity<>(articleResponseDtoPage, HttpStatus.OK);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id, @SessionAttribute(name = "email") String email, @RequestBody CreateArticleRequestDto requestDto) {
        ArticleResponseDto updatedArticle = articleService.update(id, email, requestDto);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
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
