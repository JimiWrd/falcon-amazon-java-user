package com.jumar.user.fixtures;

import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.ReadUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.models.User;
import com.jumar.user.utils.PasswordUtils;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserFixtures {

    @SneakyThrows
    public static User generateValidUser() {
        return User.builder()
                .id(1)
                .forenames("Josh").surname("Wood")
                .emailAddress("josh.wood@me.com")
                .telephone("0121123456")
                .dateOfBirth(LocalDate.parse("1994-02-04"))
                .username("josh.wood@me.com")
                .passwordHash(PasswordUtils.hashPassword("test"))
                .dateAdded(LocalDateTime.now())
                .dateLastModified(LocalDateTime.now())
                .failedLoginAttempts(0)
                .deleted(false)
                .build();

    }

    public static CreateUserDto generateCreateUserDto() {
        return CreateUserDto.builder()
                .forenames("Josh")
                .surname("Wood")
                .emailAddress("josh.wood@me.com")
                .telephone("0121123456")
                .dateOfBirth(LocalDate.parse("1994-02-04"))
                .password("test")
                .build();
    }

    public static ReadUserDto generateReadUserDto() {
        return ReadUserDto.builder()
                .forenames("Josh")
                .surname("Wood")
                .emailAddress("josh.wood@me.com")
                .telephone("0121123456")
                .dateOfBirth(LocalDate.parse("1994-02-04"))
                .build();
    }

    public static UpdateUserDto generateUpdateUserDto() {
        return UpdateUserDto.builder()
                .forenames("Joseph")
                .surname("Bloggs")
                .emailAddress("joe.bloggs@me.com")
                .telephone("01211727272")
                .dateOfBirth(LocalDate.parse("1994-02-04"))
                .password("test1")
                .build();
    }
}
