package com.example.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExpiredItemException extends RuntimeException {
    public ExpiredItemException() {
        super();
    }

    public ExpiredItemException(String message) {
        super(message);
    }

    public ExpiredItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredItemException(Throwable cause) {
        super(cause);
    }
}
