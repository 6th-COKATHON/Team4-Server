package com.example.team4.global.exception;

import com.example.team4.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.team4.global.exception.CommonErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // 처리되지 않은 모든 예외를 잡는 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e, HttpServletRequest request) {
        log.error("처리되지 않은 예외 발생: ", e);
        log.error("에러가 발생한 지점 {}, {}", request.getMethod(), request.getRequestURI());
        ErrorResponse errorResponse = ErrorResponse.of(
                INTERNAL_SERVER_ERROR,
                request
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppCustomException(AppException e, HttpServletRequest request) {
        log.error("AppException 발생: {}", e.getErrorCode().getMessage());
        log.error("에러가 발생한 지점 {}, {}", request.getMethod(), request.getRequestURI());
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), request);
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        // 첫 번째 필드 에러의 메시지 추출 (여러 개 중 하나만 대표로)
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "입력값이 유효하지 않습니다.";

        log.error("유효성 검사 실패: {}", errorMessage);
        log.error("에러가 발생한 지점 {}, {}", request.getMethod(), request.getRequestURI());

        ErrorResponse errorResponse = ErrorResponse.of(
                CommonErrorCode.INVALID_INPUT_VALUE.withDetail(errorMessage),
                request
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
