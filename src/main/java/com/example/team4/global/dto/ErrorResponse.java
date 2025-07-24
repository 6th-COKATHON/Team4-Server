package com.example.team4.global.dto;

import com.example.team4.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse extends BaseResponse {

    private final String code;
    private final String message;
    private final String method;
    private final String requestURI;

    private ErrorResponse(String code, String message, String method, String requestURI, HttpStatus httpStatus) {
        super(httpStatus);
        this.code = code;
        this.message = message;
        this.method = method;
        this.requestURI = requestURI;
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                request.getMethod(),
                request.getRequestURI(),
                errorCode.getHttpStatus()
        );
    }
}
