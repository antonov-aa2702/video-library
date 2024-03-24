package ru.red_collar.entity;

import java.util.Arrays;
import java.util.Optional;

public enum UserRole {
    ADMIN,
    USER;

    public static Optional<UserRole> find(final String role) {
        return Arrays.stream(UserRole.values())
            .filter(userRole -> userRole.name().equals(role))
            .findFirst();
    }
}
