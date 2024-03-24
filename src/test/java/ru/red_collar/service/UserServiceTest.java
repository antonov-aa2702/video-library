package ru.red_collar.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.red_collar.dao.UserDao;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.dto.UserDto;
import ru.red_collar.entity.UserEntity;
import ru.red_collar.entity.UserRole;
import ru.red_collar.exception.ValidationException;
import ru.red_collar.mapper.CreateUserMapper;
import ru.red_collar.mapper.UserMapper;
import ru.red_collar.validator.CreateUserValidator;
import ru.red_collar.validator.ValidationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private CreateUserValidator createUserValidator;

    @Mock
    private UserDao userDao;

    @Mock
    private CreateUserMapper createUserMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateIfValidationPassed() {

        CreateUserDto createUserDto = getCreateUserDto();
        UserEntity userEntity = getUserEntity();
        ValidationResult validationResult = Mockito.mock(ValidationResult.class);
        when(createUserValidator.isValid(createUserDto))
                .thenReturn(validationResult);
        when(validationResult.isValid())
                .thenReturn(true);
        when(createUserMapper.map(createUserDto))
                .thenReturn(userEntity);
        when(userDao.save(userEntity))
                .thenReturn(userEntity);

        Integer actualResult = userService.create(createUserDto);

        assertThat(actualResult)
                .isEqualTo(1);
    }

    @Test
    void testCreateIfValidationFailed() {
        CreateUserDto createUserDto = getCreateUserDto();
        UserEntity userEntity = getUserEntity();
        ValidationResult validationResult = Mockito.mock(ValidationResult.class);
        when(createUserValidator.isValid(createUserDto))
                .thenReturn(validationResult);
        when(validationResult.isValid())
                .thenReturn(false);

        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> userService.create(createUserDto));
    }


    @Test
    void testLoginSuccess() {
        UserEntity userEntity = getUserEntity();
        UserDto userDto = getUserDto();

        when(userDao.findByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword()))
                .thenReturn(Optional.of(userEntity));
        when(userMapper.map(userEntity))
                .thenReturn(userDto);

        final Optional<UserDto> actualResult = userService.login(userEntity.getEmail(), userEntity.getPassword());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(userDto);
    }

    @Test
    void testLoginFailed() {
        UserEntity userEntity = getUserEntity();

        when(userDao.findByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword()))
                .thenReturn(Optional.empty());

        final Optional<UserDto> actualResult = userService.login(userEntity.getEmail(), userEntity.getPassword());

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(userMapper);
    }

    @Test
    void testFindIdByNameIfUserExists() {
        UserEntity userEntity = Mockito.mock(UserEntity.class);

        when(userDao.findByUserName(userEntity.getName()))
                .thenReturn(Optional.of(userEntity));
        when(userEntity.getId())
                .thenReturn(1);

        final Optional<Integer> actualResult = userService.findIdByName(userEntity.getName());

        assertThat(actualResult)
                .isPresent();
        assertThat(actualResult.get())
                .isEqualTo(1);
    }

    @Test
    void testFindByIdIfUserExists() {
        UserEntity userEntity = getUserEntity();
        UserDto userDto = getUserDto();
        when(userDao.findById(1))
                .thenReturn(Optional.of(userEntity));
        when(userMapper.map(userEntity))
                .thenReturn(userDto);

        final Optional<UserDto> actualResult = userService.findById(1);

        assertThat(actualResult)
                .isPresent();
        assertThat(actualResult.get())
                .isEqualTo(userDto);
    }

    @Test
    void testFindByIdIfUserNotExists() {
        when(userDao.findById(1))
                .thenReturn(Optional.empty());

        final Optional<UserDto> actualResult = userService.findById(1);

        assertThat(actualResult)
                .isEmpty();
        verifyNoInteractions(userMapper);
    }

    @Test
    void testFindAll() {

        UserEntity userEntity = getUserEntity();
        List<UserEntity> users = List.of(userEntity);
        UserDto userDto = getUserDto();
        when(userDao.findAll())
                .thenReturn(users);
        when(userMapper.map(userEntity))
                .thenReturn(userDto);

        final List<UserDto> actualResult = userService.findAll();

        assertThat(actualResult)
                .hasSize(1);
         actualResult.stream().map(UserDto::getId)
                .forEach(id -> assertThat(id).isEqualTo(1));
    }


    private static UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .name("test")
                .email("test.email@gmail.com")
                .role(UserRole.USER)
                .birthday(LocalDate.of(2020, 10, 10))
                .build();
    }


    private static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1)
                .name("test")
                .email("test.email@gmail.com")
                .password("test")
                .role(UserRole.USER)
                .birthday(LocalDate.of(2020, 10, 10))
                .build();
    }

    private static CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .name("test")
                .email("test.email@gmail.com")
                .password("test")
                .role("USER")
                .birthday("2020-10-10")
                .build();
    }
}
