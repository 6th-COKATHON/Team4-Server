package com.example.team4.global.exception.user;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
