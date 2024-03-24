package ru.red_collar.validator;

import ru.red_collar.dto.CreateFilmDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateFilmValidatorTest {

    private final CreateFilmValidator createFilmValidator = CreateFilmValidator.getInstance();

    @Test
    void validationShouldSuccessPassValidation() {
        CreateFilmDto createFilmDto = CreateFilmDto.builder()
                .name("Один дома")
                .releaseDate("2000-10-10")
                .duration("100")
                .country("USA")
                .genre("COMEDY")
                .image(Mockito.any())
                .build();

        ValidationResult validationResult = createFilmValidator.isValid(createFilmDto);

        assertThat(validationResult.isValid())
                .isTrue();
    }

    @Test
    void validationShouldFailIfGenreInvalid() {
        CreateFilmDto createFilmDto = CreateFilmDto.builder()
                .name("Один дома")
                .releaseDate("2000-10-10")
                .duration("100")
                .country("USA")
                .image(Mockito.any())
                .build();

        ValidationResult validationResult = createFilmValidator.isValid(createFilmDto);

        assertThat(validationResult.getErrors())
                .hasSize(1);
        assertThat(validationResult.getErrors().get(0).getCode())
                .isEqualTo("invalid.genre");
    }

    @Test
    void validationShouldFailIfReleaseDateInvalid() {
        CreateFilmDto createFilmDto = CreateFilmDto.builder()
                .name("Один дома")
                .releaseDate("2000.10.10")
                .duration("100")
                .country("USA")
                .genre("MALE")
                .image(Mockito.any())
                .build();

        ValidationResult validationResult = createFilmValidator.isValid(createFilmDto);

        assertThat(validationResult.getErrors())
                .hasSize(1);
        assertThat(validationResult.getErrors().get(0).getCode())
                .isEqualTo("invalid.date");
    }

    @Test
    void validationShouldFailIfGenreAndReleaseDateInvalid() {
        CreateFilmDto createFilmDto = CreateFilmDto.builder()
                .name("Один дома")
                .releaseDate("2000.10.10")
                .duration("100")
                .country("USA")
                .image(Mockito.any())
                .build();

        ValidationResult validationResult = createFilmValidator.isValid(createFilmDto);

        assertThat(validationResult.getErrors())
                .hasSize(2);
        List<String> codes = validationResult.getErrors().stream()
                .map(Error::getCode)
                .toList();
        assertThat(codes)
                .contains("invalid.genre", "invalid.date");
    }

}