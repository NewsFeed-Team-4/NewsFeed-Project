package com.example.newsfeedproject.article.repository;

import com.example.newsfeedproject.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    default Article findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }

    List<Article> findAllByEmailOrderByCreatedAtDesc(String email);
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Optional<Article> findByIdAndEmail(Long id, String email);
}
