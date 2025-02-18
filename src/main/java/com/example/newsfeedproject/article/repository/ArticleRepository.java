package com.example.newsfeedproject.article.repository;

import com.example.newsfeedproject.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByEmailOrderByCreatedAtDesc(String email);
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Optional<Article> findByIdAndEmail(Long id, String email);

}
