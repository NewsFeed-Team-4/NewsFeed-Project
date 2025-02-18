package com.example.newsfeedproject.common.annotations.resolver;

import com.example.newsfeedproject.common.annotations.UserAuth;
import com.example.newsfeedproject.common.annotations.UserAuthDto;
import com.example.newsfeedproject.util.JwtProvider;
import com.example.newsfeedproject.util.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isType = parameter.getParameterType().equals(UserAuthDto.class);
        boolean hasAnnotation = parameter.hasParameterAnnotation(UserAuth.class);
        return isType && hasAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String tokenHeader = webRequest.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String token = tokenHeader.substring(7);

        Payload payload = jwtProvider.parseToken(token);

        return UserAuthDto.builder()
                .userId(null)//추가예정
                .userName(payload.getUsername())
                .email(payload.getEmail())
                .build();
    }
}
