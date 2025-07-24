package com.example.team4.global.exception.user;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
