package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public enum ArticleErrorCode implements ErrorCode{

    REFERENCE_ERROR(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 또는 게시판입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ArticleErrorCode(HttpStatus httpStatus, String message) {
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
