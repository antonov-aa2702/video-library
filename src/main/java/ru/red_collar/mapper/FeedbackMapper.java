package ru.red_collar.mapper;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.FeedbackDto;
import ru.red_collar.entity.FeedbackEntity;
import lombok.NoArgsConstructor;
import ru.red_collar.service.FilmService;
import ru.red_collar.service.UserService;

@NoArgsConstructor(access = PRIVATE)
public class FeedbackMapper implements Mapper<FeedbackEntity, FeedbackDto> {

    private static final FeedbackMapper INSTANCE = new FeedbackMapper();

    private final UserService userService = UserService.getInstance();

    private final FilmService filmService = FilmService.getInstance();

    public static FeedbackMapper getinstance() {
        return INSTANCE;
    }

    @Override
    public FeedbackDto map(final FeedbackEntity feedbackEntity) {
        return FeedbackDto.builder()
            .id(feedbackEntity.getId())
            .text(feedbackEntity.getText())
            .filmId(feedbackEntity.getFilmId())
            .filmName(filmService.findById(feedbackEntity.getFilmId()).get().getName())
            .userId(feedbackEntity.getUserId())
            .userName(userService.findById(feedbackEntity.getUserId()).get().getName())
            .rating(feedbackEntity.getRating())
            .date(feedbackEntity.getDate())
            .build();
    }
}
