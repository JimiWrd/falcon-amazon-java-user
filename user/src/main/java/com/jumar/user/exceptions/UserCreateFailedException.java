package com.jumar.user.exceptions;

public class UserCreateFailedException extends RuntimeException{
    public UserCreateFailedException(String message) {
        super(message);
    }

}
