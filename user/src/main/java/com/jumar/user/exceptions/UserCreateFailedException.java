package com.jumar.user.exceptions;

import java.io.Serial;

public class UserCreateFailedException extends RuntimeException{
    public UserCreateFailedException(String message) {
        super(message);
    }

}
