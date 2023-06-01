package com.jumar.user.exceptions.validation;

import com.jumar.user.dto.CreateUserDto;

public final class UserValidator {
    private UserValidator(){}

    public static boolean dtoFieldsAreNull(CreateUserDto createUserDto) {
        return createUserDto.getForenames() == null ||
               createUserDto.getSurname() == null ||
               createUserDto.getDateOfBirth() == null ||
               createUserDto.getEmailAddress() == null ||
               createUserDto.getPasswordHash() == null ||
               createUserDto.getTelephone() == null;
    }
}
