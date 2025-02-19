package com.example.newsfeedproject.user.dto.response;

import com.example.newsfeedproject.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetUserResponseDto {
    private final String username;
    private final String description;
    private final String imageUrl;
    private final Integer articleCount;
    private final Long friendCount;
    private final List<UserArticle> articleList;

    @Getter
    public static class UserArticle {
        private final Long id;
        private final String imageUrl;

        private UserArticle(Long id, String imageUrl) {
            this.id = id;
            this.imageUrl = imageUrl;
        }

        private static UserArticle of(Article article) {
            return new UserArticle(article.getId(), article.getImage());
        }

        public static List<UserArticle> ofList(List<Article> articleList) {
            return articleList.stream()
                    .map(UserArticle::of)
                    .toList();
        }
    }
}
