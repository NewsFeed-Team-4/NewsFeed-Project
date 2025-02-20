package com.example.newsfeedproject.common.exception.handler;

import com.example.newsfeedproject.common.exception.ApplicationException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    //공통예외처리
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(final ApplicationException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    //@Valid 유효성 체크
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode error = ErrorCode.METHOD_ARGUMENT_NOT_VALID;

        // 유효성 검사 실패한 모든 필드와 에러 메시지를 추출
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> errorFields = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (m1, m2) -> m1));

        ErrorResponse response = ErrorResponse.builder()
                .code(error.name())
                .message(error.getMessage())
                .build();
        response.setFieldError(errorFields);

        return ResponseEntity.status(error.getStatus()).body(response);
    }
}
