package ru.red_collar.mapper;

import jakarta.servlet.http.Part;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.red_collar.dto.CreateFilmDto;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.FilmEntity;
import ru.red_collar.entity.Genre;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateFilmMapperTest {

    private final CreateFilmMapper createFilmMapper = CreateFilmMapper.getInstance();

    @Test
    void testMapShouldReturnCorrectFilmEntity() {
        Part part = Mockito.mock(Part.class);
        Mockito.when(part.getSubmittedFileName())
                .thenReturn("\\picture.png");
        CreateFilmDto createFilmDto = getCreateFilmDto(part);
        FilmEntity expectedResult = getFilmEntity();

        FilmEntity actualResult = createFilmMapper.map(createFilmDto);

        assertThat(actualResult)
                .isEqualTo(expectedResult);
    }

    private static FilmEntity getFilmEntity() {
        return FilmEntity.builder()
                .name("Один дома")
                .releaseDate(LocalDate.of(2020, 10, 10))
                .country(Country.USA)
                .genre(Genre.ACTION)
                .duration(100)
                .image("\\films\\picture.png")
                .build();
    }

    private static CreateFilmDto getCreateFilmDto(Part part) {
        return CreateFilmDto.builder()
                .name("Один дома")
                .releaseDate("2020-10-10")
                .country("USA")
                .genre("ACTION")
                .duration("100")
                .image(part)
                .build();
    }
}