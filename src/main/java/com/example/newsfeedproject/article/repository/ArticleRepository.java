package com.example.newsfeedproject.article.repository;

import com.example.newsfeedproject.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

}
