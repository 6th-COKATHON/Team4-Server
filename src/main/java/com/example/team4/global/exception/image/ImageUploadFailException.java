package com.example.team4.global.exception.image;

import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.ErrorCode;

public class ImageUploadFailException extends AppException {
    public ImageUploadFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
