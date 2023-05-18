package com.jumar.user.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void converted_dateOfBirth_matches_correct_LocalDateTime_format() {
        LocalDate expectedDate = LocalDate.of(1994, 4, 2);
        String dateOfBirth = "1994-04-02";

        assertEquals(expectedDate, DateUtils.dateOfBirthFormatter(dateOfBirth));
    }
}