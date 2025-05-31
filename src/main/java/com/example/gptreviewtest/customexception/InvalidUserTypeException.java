package com.example.gptreviewtest.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserTypeException extends RuntimeException{
    public InvalidUserTypeException(String message) {
        super(message);
    }
}
