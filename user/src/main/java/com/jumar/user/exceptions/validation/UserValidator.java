package com.jumar.user.exceptions.validation;

import com.jumar.user.dto.CreateUserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public final class UserValidator {
    private UserValidator(){}

    public static boolean dtoFieldsAreNull(CreateUserDto createUserDto) {
        return createUserDto.getFirstName() == null ||
               createUserDto.getLastName() == null ||
               createUserDto.getDateOfBirth() == null ||
               createUserDto.getEmailAddress() == null ||
               createUserDto.getPasswordHash() == null ||
               createUserDto.getTelephone() == null;
    }
}
