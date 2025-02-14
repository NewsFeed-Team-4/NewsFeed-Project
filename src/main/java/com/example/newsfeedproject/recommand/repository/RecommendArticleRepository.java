package com.example.newsfeedproject.recommand.repository;

import com.example.newsfeedproject.recommand.entity.RecommendArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendArticleRepository extends JpaRepository<RecommendArticle, Long> {
}
