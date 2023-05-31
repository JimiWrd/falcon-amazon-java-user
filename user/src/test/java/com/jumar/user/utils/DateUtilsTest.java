package com.jumar.user.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    @Test
    void converted_dateOfBirth_matches_correct_LocalDateTime_format() {
        LocalDate expectedDate = LocalDate.of(1994, 4, 2);
        String dateOfBirth = "1994-04-02";

        assertEquals(expectedDate, DateUtils.dateOfBirthFormatter(dateOfBirth));
    }
}