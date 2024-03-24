package ru.red_collar.util;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertiesUtilTest {

    public static Stream<Arguments> getArgumentsForTest() {
        return Stream.of(Arguments.of("db.url", "jdbc:postgresql://localhost:5432/video_library_repository"),
            Arguments.of("db.user", ""),
            Arguments.of("db.password", "123"));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForTest")
    void get(final String key, final String expectedResult) {
        Assertions.assertThat(PropertiesUtil.get(key))
            .isEqualTo(expectedResult);
    }
}