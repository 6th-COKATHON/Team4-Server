package com.example.team4.global.exception.user;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class EmailAlreadyExistsException extends AppException {
    public EmailAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
