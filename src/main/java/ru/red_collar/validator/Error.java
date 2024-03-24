package ru.red_collar.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;

    String message;
}
