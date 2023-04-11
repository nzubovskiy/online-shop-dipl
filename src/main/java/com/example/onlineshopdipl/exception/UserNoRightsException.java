package com.example.onlineshopdipl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNoRightsException extends RuntimeException {
    public UserNoRightsException(String s) {
    }
}
