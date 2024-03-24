package ru.red_collar.mapper;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.CreateFeedbackDto;
import ru.red_collar.entity.FeedbackEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CreateFeedbackMapper implements Mapper<CreateFeedbackDto, FeedbackEntity> {

    private static final CreateFeedbackMapper INSTANCE = new CreateFeedbackMapper();

    public static CreateFeedbackMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public FeedbackEntity map(final CreateFeedbackDto createFeedbackDto) {
        return FeedbackEntity.builder()
            .filmId(createFeedbackDto.getFilmId())
            .userId(createFeedbackDto.getUserId())
            .text(createFeedbackDto.getText())
            .rating(createFeedbackDto.getRating())
            .date(createFeedbackDto.getDate())
            .build();
    }
}
