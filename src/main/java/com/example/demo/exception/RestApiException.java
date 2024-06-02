package com.example.demo.exception;

import com.example.demo.exception.error.ErrorCode;

public class RestApiException extends RuntimeException{

    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
