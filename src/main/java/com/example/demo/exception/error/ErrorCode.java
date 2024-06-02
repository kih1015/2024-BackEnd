package com.example.demo.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String getMessage();

}
