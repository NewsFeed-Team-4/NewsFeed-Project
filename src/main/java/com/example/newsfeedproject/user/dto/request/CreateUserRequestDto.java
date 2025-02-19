package com.example.newsfeedproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "사용자 이름을 입력 해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력 해주세요.")
    private String password;

    private String description;

    private String imageUrl;
}
