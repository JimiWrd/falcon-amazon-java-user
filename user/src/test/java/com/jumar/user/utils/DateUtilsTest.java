package com.jumar.user.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {
    private String dateOfBirth;

    @Test
    void converted_dateOfBirth_matches_correct_LocalDateTime_format() {
        LocalDateTime expectedDate = LocalDateTime.of(1994, 4, 2, 0, 0, 0);
        dateOfBirth = "1994-04-02T00:00:00";

        assertEquals(expectedDate, DateUtils.dateOfBirthFormatter(dateOfBirth));
    }

    @Test
    void dateOfBirth_time_always_set_to_midnight() {
        dateOfBirth = "1994-04-02T02:03:04";

        assertEquals("1994-04-02T00:00", DateUtils.dateOfBirthFormatter(dateOfBirth).toString());
    }

}