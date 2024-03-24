package ru.red_collar.mapper;

import lombok.NoArgsConstructor;
import ru.red_collar.dto.UpdateFilmDto;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.FilmEntity;
import ru.red_collar.entity.Genre;
import ru.red_collar.util.LocalDateUtil;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UpdateFilmMapper implements Mapper<UpdateFilmDto, FilmEntity> {

    private static final UpdateFilmMapper INSTANCE = new UpdateFilmMapper();

    private static final String IMAGE_FOLDER = "\\films";

    @Override
    public FilmEntity map(final UpdateFilmDto updateFilmDto) {
        return FilmEntity.builder()
                .id(Integer.parseInt(updateFilmDto.getId()))
                .name(updateFilmDto.getName())
                .releaseDate(LocalDateUtil.format(updateFilmDto.getReleaseDate()))
                .country(Country.valueOf(updateFilmDto.getCountry()))
                .genre(Genre.valueOf(updateFilmDto.getGenre()))
                .duration(Integer.valueOf(updateFilmDto.getDuration()))
                .image(IMAGE_FOLDER + updateFilmDto.getImage().getSubmittedFileName())
                .build();

    }

    public static UpdateFilmMapper getInstance() {
        return INSTANCE;
    }
}
