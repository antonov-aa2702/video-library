package ru.red_collar.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Genre {
    ACTION,
    COMEDY,
    MELODY;

    public static Optional<Genre> find(final String otherGenre) {
        return Arrays.stream(values())
            .filter(genre -> genre.name().equals(otherGenre))
            .findFirst();
    }
}
