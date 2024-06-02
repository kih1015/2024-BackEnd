package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public enum BoardErrorCode implements ErrorCode{

    ARTICLE_EXISTENCE(HttpStatus.BAD_REQUEST, "작성된 게시물이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    BoardErrorCode(HttpStatus httpStatus, String message) {
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
