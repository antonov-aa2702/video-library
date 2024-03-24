package ru.red_collar.util;

import java.time.LocalDate;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LocalDateUtilTest {

    public static Stream<Arguments> getArgumentsForValidationTest() {
        return Stream.of(
            of(null, false),
            of("2024.01.03", false),
            of("2024-01-03", true)
        );
    }

    @Test
    void format() {
        final String date = "2024-01-03";

        final LocalDate expectedResult = LocalDateUtil.format(date);

        assertThat(expectedResult)
            .isEqualTo(LocalDate.of(2024, 1, 3));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForValidationTest")
    void isValidShouldReturnCorrectValidationResult(final String date, final boolean expectedResult) {
        assertThat(LocalDateUtil.isValid(date))
            .isEqualTo(expectedResult);
    }
}