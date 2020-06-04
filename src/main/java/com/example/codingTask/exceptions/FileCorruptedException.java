package com.example.codingTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author BUmerle
 */

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class FileCorruptedException extends RuntimeException {

    public FileCorruptedException(String message) {
        super(message);
    }
}
