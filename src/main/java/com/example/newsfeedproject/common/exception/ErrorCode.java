package com.example.newsfeedproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("존재하지 않는 사용자 입니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST("이미 존재하는 사용자 입니다.", HttpStatus.BAD_REQUEST),
    ARTICLE_NOT_FOUND("존재하지 않는 게시글 입니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_NOT_VALID("입력값이 올바르지 않습니다", HttpStatus.BAD_REQUEST),

    //게시글 추천
    SELF_RECOMMEND_NOT_ALLOWED("본인이 작성한 게시물에는 좋아요를 남길 수 없습니다.", HttpStatus.BAD_REQUEST), // 🔹 추가
    ALREADY_RECOMMENDED("이미 추천한 게시글입니다.", HttpStatus.CONFLICT),
    RECOMMENDATION_NOT_FOUND("추천한 기록이 없습니다.", HttpStatus.NOT_FOUND),

    // 프로필 관리 관련 예외
    PASSWORD_MISMATCH("현재 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD("현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_FORMAT("비밀번호 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // 뉴스피드 게시물 관련 예외
    UNAUTHORIZED_ARTICLE_MODIFICATION("게시물 수정 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED_ARTICLE_DELETION("게시물 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 사용자 인증 관련 예외
    EMAIL_ALREADY_EXISTS("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_EMAIL_FORMAT("이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT("비밀번호 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    // 회원 탈퇴 관련 예외
    INVALID_CREDENTIALS("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    ALREADY_DEACTIVATED_USER("이미 탈퇴한 사용자입니다.", HttpStatus.GONE);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.status = httpStatus;
    }
}
