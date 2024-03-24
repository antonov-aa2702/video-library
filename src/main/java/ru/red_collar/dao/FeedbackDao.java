package ru.red_collar.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.red_collar.entity.FeedbackEntity;
import lombok.SneakyThrows;
import ru.red_collar.util.ConnectionManager;

public class FeedbackDao implements Dao<Integer, FeedbackEntity> {

    private static final FeedbackDao INSTANCE = new FeedbackDao();

    private static final String FIND_BY_FILM_ID_SQL = """
        SELECT id, id_film, id_user, text, rating, date
        FROM video_library_repository.video_library_storage.feedback
        WHERE id_film = ?
        """;

    private static final String SAVE_SQL = """
        INSERT INTO video_library_repository.video_library_storage.feedback(id_film, id_user, text, rating, date)
        VALUES (?, ?, ?, ?, ?)
        """;

    private static final String FIND_BY_USER_ID = """
        SELECT id, id_film, id_user, text, rating, date
        FROM video_library_repository.video_library_storage.feedback
        WHERE id_user = ?
        """;

    private static final String DELETE_SQL = """
        DELETE FROM video_library_repository.video_library_storage.feedback
        WHERE id = ?
        """;

    public static FeedbackDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public List<FeedbackEntity> findByFilmId(final Integer filmId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_FILM_ID_SQL)) {

            preparedStatement.setObject(1, filmId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FeedbackEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFeedbackEntity(resultSet));
            }
            return result;
        }
    }


    @SneakyThrows
    private FeedbackEntity buildFeedbackEntity(final ResultSet resultSet) {
        return FeedbackEntity.builder()
            .id(resultSet.getObject("id", Integer.class))
            .filmId(resultSet.getObject("id_film", Integer.class))
            .userId(resultSet.getObject("id_user", Integer.class))
            .text(resultSet.getObject("text", String.class))
            .rating(resultSet.getObject("rating", Short.class))
            .date(resultSet.getObject("date", LocalDateTime.class))
            .build();
    }

    @Override
    @SneakyThrows
    public FeedbackEntity save(final FeedbackEntity feedbackEntity) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                 Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, feedbackEntity.getFilmId());
            preparedStatement.setObject(2, feedbackEntity.getUserId());
            preparedStatement.setObject(3, feedbackEntity.getText());
            preparedStatement.setObject(4, feedbackEntity.getRating());
            preparedStatement.setObject(5, feedbackEntity.getDate());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                feedbackEntity.setId(generatedKeys.getObject(1, Integer.class));
            }
            return feedbackEntity;
        }
    }

    @Override
    public Optional<FeedbackEntity> findById(final Integer id) {
        return Optional.empty();
    }

    @Override
    public List<FeedbackEntity> findAll() {
        return null;
    }

    @Override
    public void update(final FeedbackEntity entity) {

    }

    @Override
    @SneakyThrows
    public boolean delete(final Integer feedbackId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, feedbackId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public List<FeedbackEntity> findByUserId(final Integer userId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setObject(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FeedbackEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFeedbackEntity(resultSet));
            }
            return result;
        }
    }
}
