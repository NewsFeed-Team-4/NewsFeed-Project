package com.example.newsfeedproject.login.service;

import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.service.UserService;
import com.example.newsfeedproject.util.JwtProvider;
import com.example.newsfeedproject.util.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public String verifyUser(String email, String password) {

        User verified = userService.verifyUserOrElseThrow(email, password);

        return jwtProvider.generateToken(Payload.of(verified.getEmail(), verified.getUsername(), new Date()));
    }
}
