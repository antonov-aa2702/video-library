package ru.red_collar.validator;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.CreateFilmDto;
import lombok.AllArgsConstructor;
import ru.red_collar.util.LocalDateUtil;

@AllArgsConstructor(access = PRIVATE)
public class CreateFilmValidator implements Validator<CreateFilmDto> {

    private static final CreateFilmValidator INSTANCE = new CreateFilmValidator();

    @Override
    public ValidationResult isValid(final CreateFilmDto createFilmDto) {
        final ValidationResult validationResult = new ValidationResult();
        if (createFilmDto.getGenre() == null) {
            validationResult.add(Error.of("invalid.genre", "Genre is invalid"));
        }
        if (!LocalDateUtil.isValid(createFilmDto.getReleaseDate())) {
            validationResult.add(Error.of("invalid.date", "Date is invalid"));
        }
        return validationResult;
    }

    public static CreateFilmValidator getInstance() {
        return INSTANCE;
    }
}
