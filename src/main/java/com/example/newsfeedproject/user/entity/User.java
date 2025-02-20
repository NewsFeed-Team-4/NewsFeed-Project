package com.example.newsfeedproject.user.entity;

import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.common.entity.BaseEntity;
import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.friend.entity.UserFriend;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Long friendCount = 0L;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Article> articles = new ArrayList<>();

    @Builder
    public User(String username, String email, String password, String description, String imageUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void updateUserInfo(String username, String description, String imageUrl) {
        this.username = username != null ? username : this.username;
        this.description = description != null ? description : this.description;
        this.imageUrl = imageUrl != null ? imageUrl : this.imageUrl;
    }

    public void updateUserPassword(String newPassword) {
        if (this.password.equals(newPassword)) {
            throw new ApplicationException(ErrorCode.PASSWORD_SAME_AS_OLD);
        }
        this.password = newPassword;
    }

    public void incrementFriendCount() {
        this.friendCount++;
    }

    public void decrementFriendCount() {
        this.friendCount--;
    }
}
