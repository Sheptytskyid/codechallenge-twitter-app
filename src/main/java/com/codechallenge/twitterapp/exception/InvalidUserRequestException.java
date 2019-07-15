package com.codechallenge.twitterapp.exception;

public class InvalidUserRequestException extends RuntimeException {
    public InvalidUserRequestException(String message) {
        super(message);
    }

    public InvalidUserRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
