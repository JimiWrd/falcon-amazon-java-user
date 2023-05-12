package com.jumar.user.exceptions;

import java.io.Serial;

public class UserCreateFailedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1;

    public UserCreateFailedException(String message) {
        super(message);
    }

}
