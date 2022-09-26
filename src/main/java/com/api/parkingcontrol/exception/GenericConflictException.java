package com.api.parkingcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GenericConflictException extends RuntimeException{
    public GenericConflictException(String message) {
        super(message);
    }
}
