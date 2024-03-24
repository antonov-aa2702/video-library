package ru.red_collar.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackDto {
    Integer id;

    String text;

    Integer filmId;

    String filmName;

    Integer userId;

    String userName;

    Short rating;

    LocalDateTime date;
}
