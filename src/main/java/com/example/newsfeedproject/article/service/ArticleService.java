package com.example.newsfeedproject.article.service;


import com.example.newsfeedproject.article.repository.ArticleRepository;
import com.example.newsfeedproject.recommand.repository.RecommendArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RecommendArticleRepository recommendArticleRepository;

    //게시글 CRUD
    // 게시글 좋아요
}
