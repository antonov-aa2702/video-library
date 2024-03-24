package ru.red_collar.util;

import java.time.LocalDate;
import ru.red_collar.dto.FilmDto;
import ru.red_collar.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ComparatorByDurationTest {

    @Test
    void compareByAscForDifferentDuration() {
        final Order order = Order.ASC;
        final ComparatorByDuration comparator = new ComparatorByDuration(order);
        final FilmDto filmDto1 = getFilmDto(200);
        final FilmDto filmDto2 = getFilmDto(100);

        final int actualResult1 = comparator.compare(filmDto1, filmDto2);
        final int actualResult2 = comparator.compare(filmDto2, filmDto1);
        final int actualResult3 = comparator.compare(filmDto1, filmDto1);

        Assertions.assertThat(actualResult1)
            .isEqualTo(1);

        Assertions.assertThat(actualResult2)
            .isEqualTo(-1);

        Assertions.assertThat(actualResult3)
            .isEqualTo(0);
    }

    private static FilmDto getFilmDto(final Integer duration) {
        return FilmDto.builder()
            .id(1)
            .name("film")
            .image("images/image1.png")
            .duration(duration)
            .releaseDate(LocalDate.of(2024, 1, 3))
            .description("description")
            .build();
    }
}