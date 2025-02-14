package com.example.newsfeedproject.recommand.repository;

import com.example.newsfeedproject.recommand.entity.RecommendArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecommendArticleRepository extends JpaRepository<RecommendArticle, Long> {
}
