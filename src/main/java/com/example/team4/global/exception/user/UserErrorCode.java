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
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못되었습니다.", "USER-004"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다. 토큰이 없거나 유효하지 않습니다.","USER-006" );
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
