package ru.red_collar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.red_collar.entity.UserEntity;
import ru.red_collar.entity.UserRole;
import lombok.SneakyThrows;
import ru.red_collar.util.ConnectionManager;

public class UserDao implements Dao<Integer, UserEntity> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_BY_EMAIL_SQL = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        WHERE email = ?
        """;

    public static final String SAVE_SQL = """
        INSERT INTO video_library_repository.video_library_storage."user"(name, password, email, role, birthday) 
        VALUES (?, ?, ?, ?, ?);
        """;

    public static final String FIND_BY_EMAIL_AND_PASSWORD = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        WHERE email = ? AND password = ?;
        """;

    private static final String FIND_BY_USER_NAME = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        WHERE name = ?
        """;

    private static final String FIND_BY_ID_SQL = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        WHERE id = ?
        """;

    private static final String FIND_ALL_SQL = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        """;

    private static final String FIND_BY_NAME = """
        SELECT id, name, password, email, role, birthday
        FROM video_library_repository.video_library_storage."user"
        WHERE name = ?
        """;

    @SneakyThrows
    public Optional<UserEntity> findByEmailAndPassword(final String email, final String password) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {

            preparedStatement.setObject(1, email);
            preparedStatement.setObject(2, password);

            final ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity userEntity = null;
            if (resultSet.next()) {
                userEntity = buildUserEntity(resultSet);
            }
            return Optional.ofNullable(userEntity);
        }
    }


    @SneakyThrows
    public Optional<UserEntity> findByEmail(final String email) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            preparedStatement.setObject(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity userEntity = null;
            if (resultSet.next()) {
                userEntity = buildUserEntity(resultSet);
            }
            return Optional.ofNullable(userEntity);
        }
    }

    @Override
    @SneakyThrows
    public UserEntity save(final UserEntity userEntity) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                 Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, userEntity.getName());
            preparedStatement.setObject(2, userEntity.getPassword());
            preparedStatement.setObject(3, userEntity.getEmail());
            preparedStatement.setObject(4, String.valueOf(userEntity.getRole()));
            preparedStatement.setObject(5, userEntity.getBirthday());
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                userEntity.setId(resultSet.getObject(1, Integer.class));
            }
        }
        return userEntity;
    }


    @Override
    @SneakyThrows
    public Optional<UserEntity> findById(final Integer userId) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUserEntity(resultSet));
            }
            return Optional.empty();
        }
    }

    @Override
    @SneakyThrows
    public List<UserEntity> findAll() {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<UserEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserEntity(resultSet));
            }
            return result;
        }
    }

    @Override
    public void update(final UserEntity entity) {

    }

    @Override
    public boolean delete(final Integer id) {
        return false;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    private UserEntity buildUserEntity(final ResultSet resultSet) {
        return UserEntity.builder().id(resultSet.getObject("id", Integer.class))
            .name(resultSet.getObject("name", String.class)).password(resultSet.getObject("password", String.class))
            .email(resultSet.getObject("email", String.class))
            .role(UserRole.find(resultSet.getObject("role", String.class)).orElse(null))
            .birthday(resultSet.getObject("birthday", LocalDate.class)).build();
    }

    @SneakyThrows
    public Optional<UserEntity> findByUserName(final String userName) {
        try (Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_NAME)) {
            preparedStatement.setObject(1, userName);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUserEntity(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public Optional<UserEntity> findByName(final String userName) {
        try (final Connection connection = ConnectionManager.get();
             final PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {

            preparedStatement.setObject(1, userName);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUserEntity(resultSet));
            }
            return Optional.empty();
        }
    }
}
