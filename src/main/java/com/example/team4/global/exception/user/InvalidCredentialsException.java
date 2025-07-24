package com.example.team4.global.exception.user;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class InvalidCredentialsException extends AppException {
    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
