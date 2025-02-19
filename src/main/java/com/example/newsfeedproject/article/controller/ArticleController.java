package com.example.newsfeedproject.article.controller;

import com.example.newsfeedproject.article.dto.ArticleResponseDto;
import com.example.newsfeedproject.article.dto.CreateArticleRequestDto;
import com.example.newsfeedproject.article.service.ArticleService;
import com.example.newsfeedproject.common.annotations.UserAuth;
import com.example.newsfeedproject.common.annotations.UserAuthDto;
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

    //==================== 게시글 CRUD API ====================//

    // 새로운 게시글을 생성
    @PostMapping
    public ResponseEntity<ArticleResponseDto> save(@RequestBody CreateArticleRequestDto requestDto, @UserAuth UserAuthDto userAuthDto) {

        ArticleResponseDto articleResponseDto = articleService.save(requestDto.getTitle(), requestDto.getContent(), userAuthDto);

        return new ResponseEntity<>(articleResponseDto, HttpStatus.CREATED);
    }

    // 게시글 목록을 페이징하여 조회
    @GetMapping
    public ResponseEntity<Page<ArticleResponseDto>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ArticleResponseDto> articleResponseDtoPage = articleService.findAll(pageable);
        return new ResponseEntity<>(articleResponseDtoPage, HttpStatus.OK);
    }


    // 특정 게시글을 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id, @UserAuth UserAuthDto userAuthDto, @RequestBody CreateArticleRequestDto requestDto) {
        ArticleResponseDto updatedArticle = articleService.update(id, userAuthDto.getEmail(), requestDto);

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // 특정 게시글을 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @UserAuth UserAuthDto userAuthDto) {

        articleService.delete(id, userAuthDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //===================== 게시글 좋아요 관련 API ==========================//

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
