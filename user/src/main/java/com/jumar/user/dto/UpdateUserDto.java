package com.jumar.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
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
