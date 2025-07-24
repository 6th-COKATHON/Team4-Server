package com.example.team4.global.exception.image;

import com.example.team4.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {

    IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.", "IMAGE-001")
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
