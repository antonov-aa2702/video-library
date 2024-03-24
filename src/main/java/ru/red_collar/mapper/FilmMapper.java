package ru.red_collar.mapper;

import ru.red_collar.dto.FilmDto;
import ru.red_collar.entity.FilmEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmMapper implements Mapper<FilmEntity, FilmDto> {

    private static final FilmMapper INSTANCE = new FilmMapper();

    public static FilmMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FilmDto map(final FilmEntity filmEntity) {
        return FilmDto.builder()
            .id(filmEntity.getId())
            .name(filmEntity.getName())
            .releaseDate(filmEntity.getReleaseDate())
            .image(filmEntity.getImage())
            .duration(filmEntity.getDuration())
            .description(String.format("DATE: %s%nCOUNTRY: %s%n GENRE: %s%n DURATION: %s",
                filmEntity.getReleaseDate(),
                filmEntity.getCountry(),
                filmEntity.getGenre(),
                filmEntity.getDuration()))
            .build();
    }
}
