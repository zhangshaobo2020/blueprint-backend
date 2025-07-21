package com.zsb.blueprint.backend.web.unify;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public WebResult<?> handleException(Exception e) {
        e.printStackTrace();
        return WebResult.failure(e.getMessage());
    }
}
