package com.example.demo.exception;

import com.example.demo.exception.error.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse rsp = new ErrorResponse(errorCode.getHttpStatus().toString(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(rsp);
    }

}
