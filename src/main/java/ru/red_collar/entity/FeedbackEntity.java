package ru.red_collar.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackEntity {
    private Integer id;

    private Integer filmId;

    private Integer userId;

    private String text;

    private Short rating;

    private LocalDateTime date;

}
