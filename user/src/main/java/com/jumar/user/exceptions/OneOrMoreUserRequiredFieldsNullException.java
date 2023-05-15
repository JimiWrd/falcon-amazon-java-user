package com.jumar.user.exceptions;

import java.io.Serial;

public class OneOrMoreUserRequiredFieldsNullException extends IllegalArgumentException{
    @Serial
    private static final long serialVersionUID = 2;

    public OneOrMoreUserRequiredFieldsNullException(String message) {
        super(message);
    }
}
