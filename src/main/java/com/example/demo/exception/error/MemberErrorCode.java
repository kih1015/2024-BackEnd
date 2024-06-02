package com.example.demo.exception.error;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode{

    EMAIL_CONFLICT(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    ARTICLE_EXISTENCE(HttpStatus.BAD_REQUEST, "작성한 게시물이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    MemberErrorCode(HttpStatus httpStatus, String message) {
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
