package com.example.newsfeedproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력 해주세요.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).+$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.")
    private final String password;
}
