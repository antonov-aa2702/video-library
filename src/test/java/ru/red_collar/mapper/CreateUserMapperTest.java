package ru.red_collar.mapper;

import org.junit.jupiter.api.Test;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.entity.UserEntity;
import ru.red_collar.entity.UserRole;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserMapperTest {

    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    @Test
    void testMapShouldReturnCorrectUserEntity() {
        CreateUserDto createUserDto = getCreateUserDto();
        UserEntity expectedResult = getUserEntity();

        UserEntity actualResult = createUserMapper.map(createUserDto);

        assertThat(actualResult)
                .isEqualTo(expectedResult);
    }

    private static UserEntity getUserEntity() {
        return UserEntity.builder()
                .name("name")
                .birthday(LocalDate.of(2020, 10, 10))
                .password("password")
                .email("test.email@gmail.com")
                .role(UserRole.USER)
                .build();
    }

    private static CreateUserDto getCreateUserDto() {
        return CreateUserDto.builder()
                .name("name")
                .birthday("2020-10-10")
                .password("password")
                .email("test.email@gmail.com")
                .role("USER")
                .build();
    }
}
