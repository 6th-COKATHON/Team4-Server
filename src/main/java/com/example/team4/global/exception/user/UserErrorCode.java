package com.example.team4.global.exception.user;

import com.example.team4.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    //400
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.", "USER-001"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.", "USER-002"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다.", "USER-003"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다.", "USER-004"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", "USER-005"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다. 토큰이 없거나 유효하지 않습니다.","USER-006" );
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
