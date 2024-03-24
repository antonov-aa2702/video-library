package ru.red_collar.service;

import lombok.Getter;
import ru.red_collar.dao.FeedbackDao;
import ru.red_collar.dto.CreateFeedbackDto;
import ru.red_collar.dto.FeedbackDto;
import ru.red_collar.entity.FeedbackEntity;
import ru.red_collar.mapper.CreateFeedbackMapper;
import ru.red_collar.mapper.FeedbackMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FeedbackService {

    private static final FeedbackService INSTANCE = new FeedbackService();

    private final FeedbackDao feedbackDao;

    private final FeedbackMapper feedbackMapper;

    private final CreateFeedbackMapper createFeedbackMapper;

    private final UserService userService;

    public static FeedbackService getInstance() {
        return INSTANCE;
    }

    private FeedbackService() {
        this(FeedbackDao.getInstance(),
                FeedbackMapper.getinstance(),
                CreateFeedbackMapper.getInstance(),
                UserService.getInstance());
    }

    private FeedbackService(FeedbackDao feedbackDao,
                            FeedbackMapper feedbackMapper,
                            CreateFeedbackMapper createFeedbackMapper,
                            UserService userService) {
        this.feedbackDao = feedbackDao;
        this.feedbackMapper = feedbackMapper;
        this.createFeedbackMapper = createFeedbackMapper;
        this.userService = userService;
    }

    public List<FeedbackDto> findByFilmId(final Integer filmId) {
        return feedbackDao.findByFilmId(filmId).stream()
                .map(feedbackMapper::map)
                .sorted(Comparator.comparing(FeedbackDto::getDate).reversed())
                .toList();
    }

    public Integer create(final CreateFeedbackDto createFeedbackDto) {
        final FeedbackEntity feedbackEntity = createFeedbackMapper.map(createFeedbackDto);
        return feedbackDao.save(feedbackEntity).getId();
    }

    public List<FeedbackDto> findByUserName(final String userName) {
        final Optional<Integer> idByName = userService.findIdByName(userName);
        return idByName.map(integer -> feedbackDao.findByUserId(integer).stream()
                        .map(feedbackMapper::map)
                        .toList())
                .orElseGet(List::of);
    }

    public boolean remove(final Integer feedbackId) {
        return feedbackDao.delete(feedbackId);
    }
}
