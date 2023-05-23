package com.jumar.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CreateUserDto {
    @NotNull(message = "Forename cannot be empty")
    private String forenames;
    @NotNull(message = "Surname cannot be empty")
    private String surname;
    @Email(message = "Email must contain a valid email address.", regexp = ".+@.+\\..+")
    @NotNull
    private String emailAddress;
    @NotNull(message = "Telephone must not be empty.")
    private String telephone;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull(message = "Password cannot be empty.")
    private String passwordHash;
}
