package ru.red_collar.mapper;

import ru.red_collar.dto.UserDto;
import ru.red_collar.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto map(final UserEntity userEntity) {
        return UserDto.builder()
            .id(userEntity.getId())
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .birthday(userEntity.getBirthday())
            .role(userEntity.getRole())
            .build();
    }
}
