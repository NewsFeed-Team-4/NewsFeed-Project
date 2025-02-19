package com.example.newsfeedproject.login.controller;

import com.example.newsfeedproject.login.service.LoginService;
import com.example.newsfeedproject.user.dto.request.GetUserRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            HttpServletResponse response,
            @RequestBody GetUserRequestDto dto
    ) {
        String jwt = loginService.verifyUser(dto.getEmail(), dto.getPassword());
        response.addHeader("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok().build();
    }
}
