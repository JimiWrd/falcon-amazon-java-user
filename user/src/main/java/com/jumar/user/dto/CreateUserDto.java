package com.jumar.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String telephone;
    private LocalDateTime dateOfBirth;
    private String passwordHash;
}
