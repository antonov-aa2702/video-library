package ru.red_collar.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Order {
    ASC,
    DESC;

    public static Optional<Order> find(final String anotherOrder) {
        return Arrays.stream(values())
            .filter(order -> order.name().equals(anotherOrder))
            .findFirst();
    }
}
