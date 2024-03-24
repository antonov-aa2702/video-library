package ru.red_collar.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.red_collar.dao.FeedbackDao;
import ru.red_collar.dto.CreateFeedbackDto;
import ru.red_collar.dto.FeedbackDto;
import ru.red_collar.entity.FeedbackEntity;
import ru.red_collar.mapper.CreateFeedbackMapper;
import ru.red_collar.mapper.FeedbackMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    private FeedbackDao feedbackDao;

    @Mock
    private FeedbackMapper feedbackMapper;

    @Mock
    private CreateFeedbackMapper createFeedbackMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private FeedbackService feedbackService;


    @Test
    void testFindByFilmId() {
        int filmId = 1;
        FeedbackEntity feedbackEntity = getFeedbackEntity(filmId);
        FeedbackDto feedbackDto = getFeedbackDto(filmId);
        when(feedbackDao.findByFilmId(filmId))
                .thenReturn(List.of(feedbackEntity));
        when(feedbackMapper.map(feedbackEntity))
                .thenReturn(feedbackDto);

        List<FeedbackDto> actualResult = feedbackService.findByFilmId(filmId);

        assertThat(actualResult)
                .hasSize(1);
        assertThat(actualResult.get(0))
                .isEqualTo(feedbackDto);
    }


    @Test
    void testCreate() {
        CreateFeedbackDto createFeedbackDto = getCreateFeedbackDto();
        FeedbackEntity feedbackEntity = getFeedbackEntity(1);
        when(createFeedbackMapper.map(createFeedbackDto))
                .thenReturn(feedbackEntity);
        when(feedbackDao.save(feedbackEntity))
                .thenReturn(feedbackEntity);

        int actualResult = feedbackService.create(createFeedbackDto);

        assertThat(actualResult)
                .isEqualTo(1);
    }

    @Test
    void testFindByUserName() {
        int defaultId = 1;
        String userName = "user";
        FeedbackEntity feedbackEntity = getFeedbackEntity(1);
        FeedbackDto feedbackDto = getFeedbackDto(defaultId);
        when(userService.findIdByName(userName))
                .thenReturn(Optional.of(defaultId));
        when(feedbackDao.findByUserId(defaultId))
                .thenReturn(List.of(feedbackEntity));
        when(feedbackMapper.map(feedbackEntity))
                .thenReturn(feedbackDto);

        List<FeedbackDto> actualResult = feedbackService.findByUserName(userName);

        assertThat(actualResult)
                .hasSize(1);
        assertThat(actualResult.get(0))
                .isEqualTo(feedbackDto);
    }

    private static CreateFeedbackDto getCreateFeedbackDto() {
        return CreateFeedbackDto.builder()
                .filmId(1)
                .userId(1)
                .text("text")
                .rating((short) 1)
                .date(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }

    private static FeedbackDto getFeedbackDto(int filmId) {
        return FeedbackDto.builder()
                .id(1)
                .filmId(filmId)
                .userId(1)
                .text("text")
                .rating((short) 1)
                .date(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }

    private static FeedbackEntity getFeedbackEntity(int filmId) {
        return FeedbackEntity.builder()
                .id(1)
                .filmId(filmId)
                .userId(1)
                .text("text")
                .rating((short) 1)
                .date(LocalDateTime.of(2000, 1, 1, 1, 1))
                .build();
    }
}