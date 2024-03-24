package ru.red_collar.service;

import ru.red_collar.dao.UserDao;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.dto.UserDto;
import ru.red_collar.entity.UserEntity;
import ru.red_collar.exception.ValidationException;
import ru.red_collar.mapper.CreateUserMapper;
import ru.red_collar.mapper.UserMapper;
import ru.red_collar.validator.CreateUserValidator;
import ru.red_collar.validator.ValidationResult;

import java.util.List;
import java.util.Optional;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator;

    private final UserDao userDao;

    private final CreateUserMapper createUserMapper;

    private final UserMapper userMapper;

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserService() {
        this(CreateUserValidator.getInstance(),
                UserDao.getInstance(),
                CreateUserMapper.getInstance(),
                UserMapper.getInstance());
    }

    private UserService(CreateUserValidator createUserValidator,
                        UserDao userDao,
                        CreateUserMapper createUserMapper,
                        UserMapper userMapper) {
        this.createUserValidator = createUserValidator;
        this.userDao = userDao;
        this.createUserMapper = createUserMapper;
        this.userMapper = userMapper;
    }


    public Integer create(final CreateUserDto createUserDto) {
        final ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        UserEntity userEntity = createUserMapper.map(createUserDto);
        userEntity = userDao.save(userEntity);
        return userEntity.getId();
    }

    public Optional<UserDto> login(final String email, final String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::map);
    }

    public Optional<Integer> findIdByName(final String userName) {
        return userDao.findByUserName(userName)
                .map(UserEntity::getId);
    }

    public Optional<UserDto> findById(final Integer userId) {
        return userDao.findById(userId)
                .map(userMapper::map);
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userMapper::map)
                .toList();
    }
}
