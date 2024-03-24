package ru.red_collar.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Country {
    RUSSIA,
    USA;

    public static Optional<Country> find(final String otherCountry) {
        return Arrays.stream(values())
                .filter(country -> country.name().equals(otherCountry))
                .findFirst();
    }
}
