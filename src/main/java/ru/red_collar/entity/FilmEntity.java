package ru.red_collar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmEntity {
    private Integer id;
    private String name;
    private LocalDate releaseDate;
    private Country country;
    private Genre genre;
    private Integer duration;
    private String image;
}
