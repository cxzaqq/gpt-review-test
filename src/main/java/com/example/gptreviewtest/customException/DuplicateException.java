package com.example.gptreviewtest.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateException(String message) {
        super(message);
    }
}
