package com.codechallenge.twitterapp.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userName) {
        super(userName);
    }

    public UserNotFoundException(String userName, Throwable cause) {
        super(userName, cause);
    }
}
