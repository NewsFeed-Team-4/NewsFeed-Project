package com.example.newsfeedproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("존재하지 않는 사용자 입니다.", HttpStatus.BAD_REQUEST),
    ARTICLE_NOT_FOUND("존재하지 않는 게시글 입니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_NOT_VALID("입력값이 올바르지 않습니다", HttpStatus.BAD_REQUEST),

    //recommend
    SELF_RECOMMEND_NOT_ALLOWED("본인이 작성한 게시물에는 좋아요를 남길 수 없습니다.", HttpStatus.BAD_REQUEST), // 🔹 추가
    ALREADY_RECOMMENDED("이미 추천한 게시글입니다.", HttpStatus.CONFLICT),
    RECOMMENDATION_NOT_FOUND("추천한 기록이 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }
}
