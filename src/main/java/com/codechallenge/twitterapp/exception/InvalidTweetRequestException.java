package com.codechallenge.twitterapp.exception;

public class InvalidTweetRequestException extends RuntimeException {

    public InvalidTweetRequestException(String message) {
        super(message);
    }

    public InvalidTweetRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
