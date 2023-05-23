package com.jumar.user.dto;

import com.jumar.user.models.Address;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ReadUserDto {
    private String forenames;
    private String surname;
    private String emailAddress;
    private String telephone;
    private LocalDate dateOfBirth;
}
