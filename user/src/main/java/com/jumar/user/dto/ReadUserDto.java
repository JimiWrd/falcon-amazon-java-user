package com.jumar.user.dto;

import com.jumar.user.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
