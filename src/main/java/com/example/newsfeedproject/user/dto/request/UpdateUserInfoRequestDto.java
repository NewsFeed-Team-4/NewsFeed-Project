package com.example.newsfeedproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserInfoRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "사용자 이름을 입력 해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력 해주세요.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).+$", message = "대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.")
    private String password;

    private String description;

    private String imageUrl;
}
