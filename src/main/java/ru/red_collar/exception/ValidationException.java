package ru.red_collar.exception;

import java.util.List;
import lombok.Getter;
import ru.red_collar.validator.Error;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(final List<Error> errors) {
        this.errors = errors;
    }
}
