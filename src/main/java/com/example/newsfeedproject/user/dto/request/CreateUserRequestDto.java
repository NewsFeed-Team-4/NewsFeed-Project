package com.example.newsfeedproject.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * --- 회원가입 ---
 * 1. 사용자 아이디는 이메일 형식이어야 합니다.
 * 2. 비밀번호는 Bcrypt 로 인코딩합니다.
 * 2-1. 암호화를 위한 PasswordEncoder 를 직접 만들어 사용합니다.
 * 2-2. 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.
 * 2-3. 비밀번호는 최소 8글자 이상이어야 합니다.
 **/

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {
    private String email;
    private String password;
    private String username;
    private String description;
    private String imageUrl;
}
