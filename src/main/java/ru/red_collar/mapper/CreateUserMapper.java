package ru.red_collar.mapper;

import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.entity.UserEntity;
import ru.red_collar.entity.UserRole;
import lombok.NoArgsConstructor;
import ru.red_collar.util.LocalDateUtil;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserEntity map(final CreateUserDto createUserDto) {
        return UserEntity.builder()
            .name(createUserDto.getName())
            .birthday(LocalDateUtil.format(createUserDto.getBirthday()))
            .password(createUserDto.getPassword())
            .email(createUserDto.getEmail())
            .role(UserRole.valueOf(createUserDto.getRole()))
            .build();
    }
}
