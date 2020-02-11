package com.goj.restservice.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1856394619111683781L;

    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus hs) {
        super(message);
        httpStatus = hs;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
