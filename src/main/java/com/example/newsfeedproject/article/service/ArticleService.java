package com.example.newsfeedproject.article.service;


import com.example.newsfeedproject.article.dto.ArticleResponseDto;
import com.example.newsfeedproject.article.dto.CreateArticleRequestDto;
import com.example.newsfeedproject.article.entity.Article;
import com.example.newsfeedproject.article.repository.ArticleRepository;
import com.example.newsfeedproject.common.annotations.UserAuthDto;
import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.recommand.entity.RecommendArticle;
import com.example.newsfeedproject.recommand.repository.RecommendArticleRepository;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final RecommendArticleRepository recommendArticleRepository;
    private final UserRepository userRepository;

    //=============== 게시글 CRUD 기능 ===============//

    public ArticleResponseDto save(String title, String contents, UserAuthDto userAuthDto) {

        // 이메일로 사용자 조회
        User findUser = userRepository.findByEmailOrElseThrow(userAuthDto.getEmail());

        // 게시글 엔티티 생성
        Article article = Article.builder()
                .user(findUser)
                .userName(userAuthDto.getUserName())
                .title(title)
                .content(contents)
                .email(userAuthDto.getEmail())
                .build();

        Article savedArticle = articleRepository.save(article);

        return new ArticleResponseDto(savedArticle);
    }

    // 모든 게시글을 페이징하여 조회
    public Page<ArticleResponseDto> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(ArticleResponseDto::new);
    }

    // 권한 검증 메서드
    private void validateArticleAuthor (Article article, String email) {
        if (!article.getEmail().equals(email)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED_ARTICLE_MODIFICATION);
        }

    }

    // 게시글 수정 메서드
    @Transactional
    public ArticleResponseDto update(Long id, String email, CreateArticleRequestDto requestDto) {

        // 게시글 존재 여부 확인
        Article article = articleRepository.findByIdOrElseThrow(id);

        // 수정 권한 확인
        validateArticleAuthor(article, email);

        // 게시글 수정
        article.update(requestDto.getTitle(), requestDto.getContent());

        return new ArticleResponseDto(article);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id, String email) {

        // 게시글 존재 여부 확인
        Article article = articleRepository.findByIdOrElseThrow(id);

        // 삭제 권한 확인
        validateArticleAuthor(article, email);

        // 게시글 삭제
        articleRepository.delete(article);
    }


    //================== 게시글 추천 관련 기능 ===================//

    @Transactional
    public void addRecommendArticle(Long articleId, Long userId) {
        //좋아요한 게시글과 사용자가 존재하는지 검증
        Article article = articleRepository.findByIdOrElseThrow(articleId);
        if(article.getUser().getId().equals(userId)){
            throw new ApplicationException(ErrorCode.SELF_RECOMMEND_NOT_ALLOWED);
        }
        User user = userRepository.findByIdOrElseThrow(userId);

        //중복데이터 체크
        recommendArticleRepository.findByArticleIdAndUserId(articleId, userId).ifPresent(entity -> {
            throw new ApplicationException(ErrorCode.ALREADY_RECOMMENDED);
        });

        RecommendArticle recommendArticle = RecommendArticle.builder()
                .article(article)
                .user(user)
                .build();

        recommendArticleRepository.save(recommendArticle);
        article.incrementRecommendCount();
    }

    @Transactional
    public void cancelRecommendArticle(Long articleId, Long userId) {
        Article article = articleRepository.findByIdOrElseThrow(articleId);
        Long deleted = recommendArticleRepository.deleteRecommendArticleByArticleIdAndUserId(articleId, userId);
        if (deleted == 0) {
            throw new ApplicationException(ErrorCode.RECOMMENDATION_NOT_FOUND);
        }
        article.decrementRecommendCount();
    }
}
