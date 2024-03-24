package ru.red_collar.service;

import java.util.stream.Collectors;

public class ReportService {

    private static final ReportService INSTANCE = new ReportService();

    private final FeedbackService feedbackService = FeedbackService.getInstance();

    public String create(final String userName) {
        return feedbackService.findByUserName(userName).stream()
            .map(feedbackDto -> String.format(
                "user: %s%nfilm:%s%nrating:%s%ntext:%s%n",
                feedbackDto.getUserName(),
                feedbackDto.getFilmName(),
                feedbackDto.getRating(),
                feedbackDto.getText()))
            .collect(Collectors.joining(System.lineSeparator()));
    }

    public static ReportService getInstance() {
        return INSTANCE;
    }
}
