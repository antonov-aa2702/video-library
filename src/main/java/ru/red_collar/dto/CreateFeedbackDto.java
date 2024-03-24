package ru.red_collar.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFeedbackDto {
    Integer filmId;

    Integer userId;

    String text;

    Short rating;

    LocalDateTime date;
}
