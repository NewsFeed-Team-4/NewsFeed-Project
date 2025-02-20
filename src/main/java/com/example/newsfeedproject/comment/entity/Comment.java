package com.example.newsfeedproject.comment.entity;

import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //요청 추가부분
    @Column(nullable = false, unique = true)
    private String username; // 작성자 이름

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(nullable = false)
    private String userName;

    private String content;

    @Builder
    public Comment(Comment parent, User user, Article article, String userName, String content) {
        this.parent = parent;
        this.user = user;
        this.article = article;
        this.userName = userName;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
