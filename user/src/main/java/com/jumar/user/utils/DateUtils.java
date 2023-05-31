package com.jumar.user.utils;

import java.time.LocalDate;

public final class DateUtils {
    private DateUtils() {}

    public static LocalDate dateOfBirthFormatter(String dateOfBirth) {

        return LocalDate.parse(dateOfBirth);
    }

}
