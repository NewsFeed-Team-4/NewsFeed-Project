package com.example.newsfeedproject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserPasswordRequestDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식을 지켜주세요.")
    private String email;

    @NotBlank(message = "기존 비밀번호를 입력 해주세요.")
    private String oldPassword;

    @NotBlank(message = "새로운 비밀번호를 입력 해주세요.")
    private String newPassword;
}
