package com.jumar.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserDto {
    private String forenames;
    private String surname;
    private String emailAddress;
    private String telephone;
    private LocalDate dateOfBirth;
}
