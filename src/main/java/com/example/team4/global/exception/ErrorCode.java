package com.example.team4.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getHttpStatus();
    String getMessage();
    String getCode();

    default ErrorCode withDetail(String detailMessage) {
        return new ErrorCode() {
            public HttpStatus getHttpStatus() {
                return ErrorCode.this.getHttpStatus();
            }

            public String getMessage() {
                return detailMessage;
            }

            public String getCode() {
                return ErrorCode.this.getCode();
            }
        };
    }
}
