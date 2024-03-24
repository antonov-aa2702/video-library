package ru.red_collar.validator;

public interface Validator<T> {
    ValidationResult isValid(final T object);
}
