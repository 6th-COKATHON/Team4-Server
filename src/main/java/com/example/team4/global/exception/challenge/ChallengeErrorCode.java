package com.example.team4.global.exception.challenge;

import com.example.team4.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChallengeErrorCode implements ErrorCode {

    MISSING_INPUT(HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다. 입력값이 올바르지 않습니다.", "CHALLENGE-001"),
    CYCLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사이클입니다.", "CHALLENGE-002"),
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 챌린지입니다.", "CHALLENGE-003"),
    NOT_CHALLENGE_WRITER(HttpStatus.FORBIDDEN, "작성자가 아닙니다.", "CHALLENGE-004"),
    CHALLENGE_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 챌린지입니다.", "CHALLENGE-005")
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
