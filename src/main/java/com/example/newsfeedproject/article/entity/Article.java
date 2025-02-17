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

    private String content;

    @Column(nullable = false)
    private Long recommendCount = 0L;

    @Builder
    public Article(User user, String userName, String image, String content) {
        this.user = user;
        this.userName = userName;
        this.image = image;
        this.content = content;
    }

    public void incrementRecommendCount() {
        recommendCount++;
    }

    public void decrementRecommendCount() {
        recommendCount--;
    }
}
