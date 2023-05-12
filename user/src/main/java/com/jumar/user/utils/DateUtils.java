package com.jumar.user.utils;

import java.time.LocalDateTime;

public final class DateUtils {
    private DateUtils() {}

    public static LocalDateTime dateOfBirthFormatter(String dateOfBirth) {

        return LocalDateTime.parse(dateOfBirth).toLocalDate().atStartOfDay();
    }
}
