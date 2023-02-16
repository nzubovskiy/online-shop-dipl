package com.example.onlineshopdipl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class FileSizeException extends RuntimeException{
    public FileSizeException() {
        super("The file size exceeds the set limit");
    }
}
