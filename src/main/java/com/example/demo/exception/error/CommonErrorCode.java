package com.example.demo.exception.error;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode{

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "자원이 존재하지 않습니다."),
    NULL_PARAMETER(HttpStatus.BAD_REQUEST, "NULL 파라미터가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    CommonErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
