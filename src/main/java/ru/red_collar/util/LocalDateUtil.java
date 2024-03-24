package ru.red_collar.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@UtilityClass
public class LocalDateUtil {
    private static final String PATTERN = "yyyy-MM-dd";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static LocalDate format(final String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public boolean isValid(final String date) {
        try {
            return Optional.ofNullable(date)
                    .map(LocalDateUtil::format)
                    .isPresent();
        } catch (final DateTimeParseException e) {
            return false;
        }
    }
}
