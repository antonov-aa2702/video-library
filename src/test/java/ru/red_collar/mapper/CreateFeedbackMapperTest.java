package ru.red_collar.mapper;

import org.junit.jupiter.api.Test;
import ru.red_collar.dto.CreateFeedbackDto;
import ru.red_collar.entity.FeedbackEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateFeedbackMapperTest {

    private final CreateFeedbackMapper createFeedbackMapper = CreateFeedbackMapper.getInstance();

    @Test
    void testMapShouldReturnCorrectFeedbackEntity() {
        LocalDateTime date = LocalDateTime.of(2000, 1, 1, 1, 1);
        CreateFeedbackDto createFeedbackDto = getCreateFeedbackDto(date);
        FeedbackEntity expectedResult = getFeedbackEntity(date);

        FeedbackEntity actualResult = createFeedbackMapper.map(createFeedbackDto);

        assertThat(actualResult)
                .isEqualTo(expectedResult);
    }

    private static FeedbackEntity getFeedbackEntity(LocalDateTime localDateTimeMock) {
        return FeedbackEntity.builder()
                .filmId(1)
                .userId(1)
                .text("text")
                .rating((short) 1)
                .date(localDateTimeMock)
                .build();
    }

    private static CreateFeedbackDto getCreateFeedbackDto(LocalDateTime localDateTime) {
        return CreateFeedbackDto.builder()
                .filmId(1)
                .userId(1)
                .text("text")
                .rating((short) 1)
                .date(localDateTime)
                .build();
    }
}
