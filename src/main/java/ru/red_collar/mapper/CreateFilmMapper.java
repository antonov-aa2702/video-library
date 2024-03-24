package ru.red_collar.mapper;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.CreateFilmDto;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.FilmEntity;
import ru.red_collar.entity.Genre;
import lombok.NoArgsConstructor;
import ru.red_collar.util.LocalDateUtil;

@NoArgsConstructor(access = PRIVATE)
public class CreateFilmMapper implements Mapper<CreateFilmDto, FilmEntity> {

    private static final CreateFilmMapper INSTANCE = new CreateFilmMapper();

    private static final String IMAGE_FOLDER = "\\films";

    @Override
    public FilmEntity map(final CreateFilmDto createFilmDto) {
        return FilmEntity.builder()
            .name(createFilmDto.getName())
            .releaseDate(LocalDateUtil.format(createFilmDto.getReleaseDate()))
            .country(Country.valueOf(createFilmDto.getCountry()))
            .genre(Genre.valueOf(createFilmDto.getGenre()))
            .duration(Integer.valueOf(createFilmDto.getDuration()))
            .image(IMAGE_FOLDER + createFilmDto.getImage().getSubmittedFileName())
            .build();
    }

    public static CreateFilmMapper getInstance() {
        return INSTANCE;
    }
}
