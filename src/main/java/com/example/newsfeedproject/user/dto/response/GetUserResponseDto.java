package com.example.newsfeedproject.user.dto.response;

import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.user.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUserResponseDto {
    private final String username;
    private final String description;
    private final String imageUrl;
    private final Integer articleCount;
    private final Integer friendsCount;
    private final List<UserArticle> articleList;

    public GetUserResponseDto(User user) {
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.imageUrl = user.getImageUrl();
        this.articleCount = user.getArticles().size();
        this.friendsCount = user.getUserFriends().size();
        this.articleList = UserArticle.ofList(user.getArticles());
    }

    @Getter
    private static class UserArticle {
        private final Long id;
        private final String imageUrl;

        private UserArticle(Long id, String imageUrl) {
            this.id = id;
            this.imageUrl = imageUrl;
        }

        private static UserArticle of(Article article) {
            return new UserArticle(article.getId(), article.getImage());
        }

        private static List<UserArticle> ofList(List<Article> articleList) {
            return articleList.stream()
                    .map(UserArticle::of)
                    .toList();
        }
    }
}
