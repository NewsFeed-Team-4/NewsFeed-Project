package com.example.newsfeedproject.filter;


import com.example.newsfeedproject.util.JwtProvider;
import com.example.newsfeedproject.util.Payload;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/users/signup", "/login", "/logout"};
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // WHITE_LIST 체크
        // if (PatternMatchUtils.simpleMatch(WHITE_LIST, req.getRequestURI())) { // 테스트용
        if (!PatternMatchUtils.simpleMatch(WHITE_LIST, req.getRequestURI())) {
            // JWT 검증, parse 싧패시 throw
            Payload payload = jwtProvider.parseToken(req.getHeader("Authorization").substring(7));
            System.out.println(payload.getExp());
        }

        // 성공 -> doFilter
        chain.doFilter(request, response);
    }
}
