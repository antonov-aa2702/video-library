package ru.red_collar.validator;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.UpdateFilmDto;
import lombok.NoArgsConstructor;
import ru.red_collar.util.LocalDateUtil;

@NoArgsConstructor(access = PRIVATE)
public class UpdateFilmValidator implements Validator<UpdateFilmDto> {

    private static final UpdateFilmValidator INSTANCE = new UpdateFilmValidator();

    @Override
    public ValidationResult isValid(final UpdateFilmDto updateFilmDto) {
        final ValidationResult validationResult = new ValidationResult();
        if (updateFilmDto.getGenre() == null) {
            validationResult.add(Error.of("Invalid genre", "Genre is invalid"));
        }
        if (!LocalDateUtil.isValid(updateFilmDto.getReleaseDate())) {
            validationResult.add(Error.of("Invalid date", "Date is invalid"));
        }
        return validationResult;
    }

    public static UpdateFilmValidator getInstance() {
        return INSTANCE;
    }
}
