package ru.red_collar.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFilmDto {
    String id;

    String name;

    String releaseDate;

    String country;

    String genre;

    String duration;

    Part image;
}
