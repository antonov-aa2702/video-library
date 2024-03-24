package ru.red_collar.validator;

import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StringValidator implements Validator<String> {

    private static final StringValidator INSTANCE = new StringValidator();

    public static StringValidator getInstance() {
        return INSTANCE;
    }

    private static final Set<Character> LATIN_CHARS_ONLY =
            Stream.concat(
                            Stream.iterate('a', ch -> ch != 'z' + 1, ch -> (char) (ch + 1)),
                            Stream.iterate('A', ch -> ch != 'Z' + 1, ch -> (char) (ch + 1)))
                    .collect(Collectors.toUnmodifiableSet());


    @Override
    public ValidationResult isValid(final String string) {
        final ValidationResult validationResult = new ValidationResult();
        if (!isContainsOnlyLatinCharsOnly(string)) {
            validationResult.add(Error.of("invalid.name", "Name should consist only of Latin letters"));
        }
        return validationResult;
    }

    private static boolean isContainsOnlyLatinCharsOnly(final String string) {
        if (string == null) {
            return false;
        }
        return string.chars()
                .mapToObj(c -> (char) c)
                .allMatch(LATIN_CHARS_ONLY::contains);
    }
}
