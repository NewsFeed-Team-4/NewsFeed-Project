package com.example.newsfeedproject.recommand.repository;

import com.example.newsfeedproject.recommand.entity.RecommendArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecommendArticleRepository extends JpaRepository<RecommendArticle, Long> {
    Optional<RecommendArticle> findByArticleIdAndUserId(Long articleId, Long userId);
    Long deleteRecommendArticleByArticleIdAndUserId(Long articleId, Long userId);
}
