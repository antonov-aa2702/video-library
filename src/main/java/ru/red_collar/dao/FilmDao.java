package ru.red_collar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.FilmEntity;
import ru.red_collar.entity.Genre;
import lombok.SneakyThrows;
import ru.red_collar.util.ConnectionManager;

public class FilmDao implements Dao<Integer, FilmEntity> {


    private static final FilmDao INSTANCE = new FilmDao();

    private static final String FIND_ALL_SQL = """
        SELECT id, name, release_date, country, genre, duration, image
        FROM video_library_repository.video_library_storage.film
        """;

    private static final String FIND_BY_ID_SQL = """
        SELECT id, name, release_date, country, genre, duration, image
        FROM video_library_repository.video_library_storage.film
        WHERE id = ?
        """;

    private static final String FIND_BY_NAME_FILM_SQL = """
        SELECT id, name, release_date, country, genre, duration, image
        FROM video_library_repository.video_library_storage.film
        WHERE name = ?;
        """;

    private static final String FIND_BY_GENRE_SQL = """
        SELECT id, name, release_date, country, genre, duration, image
        FROM video_library_repository.video_library_storage.film
        WHERE genre = ?;
         """;

    private static final String FIND_BY_YEAR_SQL = """
        SELECT id, name, release_date, country, genre, duration, image
        FROM video_library_repository.video_library_storage.film
        WHERE EXTRACT(YEAR FROM release_date) = ?;
        """;

    private static final String FIND_BY_ACTOR_NAME = """
        SELECT f.id, f.name, f.release_date, f.country, f.genre, f.duration, f.image
        FROM video_library_repository.video_library_storage.film f
         JOIN video_library_repository.video_library_storage.actor a on f.id = a.id_film
        WHERE a.name  = ?
        """;

    private static final String SAVE_SQL = """
        INSERT INTO video_library_repository.video_library_storage.film(name, release_date, country, genre, duration, image)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    private static final String DELETE_SQL = """
        DELETE FROM video_library_repository.video_library_storage.film
        WHERE id = ?
        """;

    private static final String UPDATE_SQL = """
        UPDATE video_library_repository.video_library_storage.film
        SET name = ?, release_date = ?, country = ?, genre = ?, duration = ?, image = ?
        WHERE id = ?
        """;

    public static FilmDao getInstance() {
        return INSTANCE;
    }

    @Override
    @SneakyThrows
    public FilmEntity save(final FilmEntity filmEntity) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, filmEntity.getName());
            preparedStatement.setObject(2, filmEntity.getReleaseDate());
            preparedStatement.setObject(3, String.valueOf(filmEntity.getCountry()));
            preparedStatement.setObject(4, String.valueOf(filmEntity.getName()));
            preparedStatement.setObject(5, filmEntity.getDuration());
            preparedStatement.setObject(6, filmEntity.getImage());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                filmEntity.setId(generatedKeys.getObject(1, Integer.class));
            }
            return filmEntity;
        }
    }

    @SneakyThrows
    public List<FilmEntity> findByActorName(final String actor) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ACTOR_NAME)) {
            preparedStatement.setObject(1, actor);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FilmEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFilmEntity(resultSet));
            }
            return result;
        }
    }

    @SneakyThrows
    public List<FilmEntity> findByNameFilm(final String filmName) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_FILM_SQL)) {
            preparedStatement.setObject(1, filmName);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FilmEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFilmEntity(resultSet));
            }
            return result;
        }
    }

    @SneakyThrows
    public List<FilmEntity> findByGenre(final String nameGenre) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_GENRE_SQL)) {
            preparedStatement.setObject(1, nameGenre);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FilmEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFilmEntity(resultSet));
            }
            return result;
        }
    }

    @Override
    @SneakyThrows
    public Optional<FilmEntity> findById(final Integer filmId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, filmId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            FilmEntity filmEntity = null;
            if (resultSet.next()) {
                filmEntity = buildFilmEntity(resultSet);
            }
            return Optional.of(filmEntity);
        }
    }

    @Override
    @SneakyThrows
    public List<FilmEntity> findAll() {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FilmEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFilmEntity(resultSet));
            }
            return result;
        }
    }

    @SneakyThrows
    private FilmEntity buildFilmEntity(final ResultSet resultSet) {
        return FilmEntity.builder()
            .id(resultSet.getObject("id", Integer.class))
            .name(resultSet.getObject("name", String.class))
            .releaseDate(resultSet.getObject("release_date", LocalDate.class))
            .genre(Genre.find(resultSet.getObject("genre", String.class)).orElse(null))
            .country(Country.find(resultSet.getObject("country", String.class)).orElse(null))
            .duration(resultSet.getObject("duration", Integer.class))
            .image(resultSet.getObject("image", String.class))
            .build();
    }

    @Override
    @SneakyThrows
    public void update(final FilmEntity filmEntity) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, filmEntity.getName());
            preparedStatement.setObject(2, filmEntity.getReleaseDate());
            preparedStatement.setObject(3, String.valueOf(filmEntity.getCountry()));
            preparedStatement.setObject(4, String.valueOf(filmEntity.getGenre()));
            preparedStatement.setObject(5, filmEntity.getDuration());
            preparedStatement.setObject(6, filmEntity.getImage());

            preparedStatement.setObject(7, filmEntity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public boolean delete(final Integer filmId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, filmId);
            final int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        }
    }

    @SneakyThrows
    public List<FilmEntity> findByYear(final Integer year) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_YEAR_SQL)) {
            preparedStatement.setObject(1, year);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<FilmEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildFilmEntity(resultSet));
            }
            return result;
        }
    }
}
