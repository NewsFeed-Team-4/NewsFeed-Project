package com.example.newsfeedproject.article.entity;

import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String userName;

    private String image;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long recommendCount = 0L;

    public Article(String title, String content, String userName) {
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Article(User user, String userName, String image, String title, String content, String email) {
        this.user = user;
        this.userName = userName;
        this.image = image;
        this.title = title;
        this.content = content;
        this.email = email;
    }

    public void incrementRecommendCount() {
        recommendCount++;
    }

    public void decrementRecommendCount() {
        recommendCount--;
    }
}
