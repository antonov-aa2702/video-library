package ru.red_collar.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FilmDto {
    Integer id;

    String name;

    String image;

    Integer duration;

    LocalDate releaseDate;

    String description;
}
