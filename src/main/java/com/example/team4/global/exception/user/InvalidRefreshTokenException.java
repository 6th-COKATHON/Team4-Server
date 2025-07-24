package com.example.team4.global.exception.user;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class InvalidRefreshTokenException extends AppException {
    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
